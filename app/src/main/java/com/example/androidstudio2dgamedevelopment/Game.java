package com.example.androidstudio2dgamedevelopment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.androidstudio2dgamedevelopment.game.gameobject.Deck;
import com.example.androidstudio2dgamedevelopment.game.gameobject.Desk;
import com.example.androidstudio2dgamedevelopment.game.gameobject.DeskManager;
import com.example.androidstudio2dgamedevelopment.game.gameobject.OpponentHand;
import com.example.androidstudio2dgamedevelopment.game.gameobject.PlayerHand;
import com.example.androidstudio2dgamedevelopment.game.gamepanel.EndTurnButton;
import com.example.androidstudio2dgamedevelopment.game.gamepanel.Performance;
import com.example.androidstudio2dgamedevelopment.game.gamepanel.ScoreBoard;
import com.example.androidstudio2dgamedevelopment.task.getMatchInfoTask;
import com.example.androidstudio2dgamedevelopment.task.postMatchInfoTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects to the screen
 */
class Game extends SurfaceView implements SurfaceHolder.Callback {
    private String logTag="Game.java";
    private int CardPointerId = 0;
    private GameLoop gameLoop;

    private final Performance performance;
    private final PlayerHand playerHand;
    private final OpponentHand opponentHand;
    private final Deck deck;
    private final DeskManager deskManager;
    private final ScoreBoard scoreBoard;
    private final EndTurnButton endTurnButton;

    private final int windowSizeX,windowSizeY;

    private final String username,password,game_name;
//    Handler handler;
//    ExecutorService es;
//    ExecutorService es2;


    private int round=0;
    private boolean turnOfPlayer=false;
    private int playerScore=0;
    private int opponentScore=0;
    private final Context context;

    Handler hand_mqtt_msg = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMsg){

            String topic = inputMsg.getData().getString("topic_name");
            String payload = inputMsg.getData().getString("payload");
            System.out.println("received MSG:" +topic+payload);
            String[] topics=topic.split("/");
            if(topic.startsWith("STC/"+game_name)){
                if (topics[2].equals("player1_hand")){
                    if (topics[1].startsWith(username)){
                        playerHand.add(deck.drawCard(Integer.parseInt(payload),false));
                    }else if(topics[1].endsWith(username)){
                        opponentHand.add(deck.drawCard(Integer.parseInt(payload),true));
                    }
                }else if (topics[2].equals("player2_hand")){
                    if (topics[1].startsWith(username)){
                        opponentHand.add(deck.drawCard(Integer.parseInt(payload),true));
                    }else if(topics[1].endsWith(username)){
                        playerHand.add(deck.drawCard(Integer.parseInt(payload),false));
                    }
                }else if(topics[2].equals("desk")){

                    String[] info = payload.split(",");
                    int deskIndex=Integer.parseInt(info[3]);
                    int type=Integer.parseInt(info[0]);
                    deskManager.add(new Desk(context,type,0,0,windowSizeX,windowSizeY,deskIndex));
                }else if(topics[2].startsWith("remove_card_hand")){
                    playerHand.discard(Integer.parseInt(payload));
                    opponentHand.discard(Integer.parseInt(payload));
                }else if(topics[2].equals("set_desk_card")) {
                    String[] info = payload.split(",");
                    if (topics[1].startsWith(username)){
                        deskManager.getDesk(Integer.parseInt(info[3]))
                                .setCard(deck.drawCard(Integer.parseInt(info[1]),false)
                                ,deck.drawCard(Integer.parseInt(info[2]),true));
                    }else if(topics[1].endsWith(username)){
                        deskManager.getDesk(Integer.parseInt(info[3])).
                                setCard(deck.drawCard(Integer.parseInt(info[2]),false)
                                ,deck.drawCard(Integer.parseInt(info[1]),true));
                    }
                }else if(topics[2].equals("round")){
                    deskManager.reset();
                    round=Integer.parseInt(payload);
                }else if(topics[2].equals("turn_of_player")){
                    if (topics[1].startsWith(username)){
                        turnOfPlayer=payload.equals("False");
                    }else if(topics[1].endsWith(username)){
                        turnOfPlayer=payload.equals("True");
                    }
                }else if(topics[2].equals("round_winner")){

                }else if(topics[2].equals("scores")){
                    if (topics[1].startsWith(username)){
                        playerScore=Integer.parseInt(payload.split(",")[0]);
                        opponentScore=Integer.parseInt(payload.split(",")[1]);
                    }else if(topics[1].endsWith(username)){
                        playerScore=Integer.parseInt(payload.split(",")[1]);
                        opponentScore=Integer.parseInt(payload.split(",")[0]);
                    }
                }
            }else  {
                Log.d("Game.java", "wrong topic: "+topic);
            }
        }
    };

    MQTTModule mqtt_handler = MQTTModule.getInstance();

    public Game(Context context,String username,String password,String game_name) {
        super(context);
        this.context=context;
        this.username=username;
        this.password=password;
        this.game_name=game_name;
        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();

        setZOrderOnTop(true);    // necessary
        //setZOrderMediaOverlay(true);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this, surfaceHolder);
        // Initialize display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        windowSizeX=displayMetrics.widthPixels;
        windowSizeY=displayMetrics.heightPixels;
        // Initialize game panels
        performance = new Performance(context, gameLoop);
        // Initialize game UI
        scoreBoard=new ScoreBoard(context,windowSizeX, windowSizeY);
        endTurnButton= new EndTurnButton(context,windowSizeX, windowSizeY);
        // Initialize game objects
        deck =new Deck(context,Utils.initializeCSV(context),windowSizeX,windowSizeY);
        playerHand= new PlayerHand(windowSizeX, windowSizeY);
        opponentHand= new OpponentHand(windowSizeX, windowSizeY);
        deskManager=new DeskManager(windowSizeX,windowSizeY);


//        es= Executors.newSingleThreadExecutor();
//        es2= Executors.newSingleThreadExecutor();
//        handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message inputMessage) {
//                String matchInfo = inputMessage.getData().getString("matchInfo");
//                Log.d(logTag, "matchInfo: "+matchInfo);
//                try {
//                    JSONObject jsonObject= new JSONObject(matchInfo);
//                    turnOfPlayer=jsonObject.getBoolean("turnOfPlayer");
//                    int cards;
//
//                    cards=jsonObject.getInt("opponentHandCards");
//                    opponentHand.reset();
//                    for (int i=0;i<cards;i++){
//                        opponentHand.add(deck.drawCard(jsonObject.getInt("opponentHand"+i),true));
//                    }
//                    int aux=jsonObject.getInt("round");
//                    if(aux==round+1) {
//                        round++;
//                        playerHand.reset();
//                        cards=jsonObject.getInt("playerHandCards");
//                        for (int i=0;i<cards;i++){
//                            playerHand.add(deck.drawCard(jsonObject.getInt("playerHand"+i),false));
//                        }
//                        playerScore = jsonObject.getInt("playerScore");
//                        opponentScore = jsonObject.getInt("opponentScore");
//                        deskNumber = jsonObject.getInt("deskNumber");
//                        deskManager.reset();
//                        for (int i=0;i<deskNumber;i++){
//
//                            Desk auxDesk=new Desk(context,jsonObject.getString("deskString"+i),
//                                    windowSizeX/(float)(deskNumber+1)*(i+1),windowSizeY/2f,windowSizeX,windowSizeY);
//                            deskManager.add(auxDesk);
//                        }
//                    }
//                    int cardToAdd=jsonObject.getInt("addCardToHand");
//                    if(cardToAdd!=0){
//                        playerHand.add(deck.drawCard(cardToAdd,false));
//                        postMatchInfo(false);
//                    }
//                    for (int i=0;i<deskNumber;i++){
//                        deskManager.getDesk(i).setOpponentCard(deck.drawCard(jsonObject.getInt("deskOpponent"+i),true));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };

//        Log.d(logTag, "Scheduling new task in background thread");
//        getMatchInfoTask task = new getMatchInfoTask(handler,username,password);
//        es.execute(task);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle user input touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                playerHand.checkAnyCardClicking(event.getX(), event.getY());
                deskManager.checkCardAnyDeskClicking(event.getX(), event.getY());
                if(turnOfPlayer&&endTurnButton.checkClicking(event.getX(), event.getY())){
                    mqtt_handler.sendMSGToTopic("CTS/"+username+"/end_turn","end_turn",2);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                playerHand.dragCardTo(event.getX(), event.getY());
                if(turnOfPlayer)deskManager.dragCardTo(event.getX(), event.getY());
                return true;

            case MotionEvent.ACTION_UP:
                if(turnOfPlayer){
                    playerHand.playCardOnDesk(deskManager,mqtt_handler,username);
                    deskManager.returnCardToHand(playerHand,mqtt_handler,username);
                }
//                postMatchInfo(false);
                playerHand.sort();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
        mqtt_handler.setHandler(hand_mqtt_msg);
        if(mqtt_handler.connectToBroker("tcp://52.148.250.153:1883")){
            mqtt_handler.subscribeToTopic("STC/"+game_name+"/#");
        }
        mqtt_handler.sendMSGToTopic("CTS/"+username+"/ready","ready",2);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Draw background
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        //background.draw(canvas);




        // Draw game objects

        deskManager.draw(canvas);
        playerHand.draw(canvas);
        opponentHand.draw(canvas);

        // Draw game panels
        performance.draw(canvas);
        // Draw game table UI

        scoreBoard.draw(canvas,playerScore,opponentScore, round,turnOfPlayer);
        endTurnButton.draw(canvas,turnOfPlayer);
    }

    public void update() {
        // Stop updating the game if the player is dead


        // Update game state

        deskManager.update();

        playerHand.update();
        opponentHand.update();
        // Iterate through enemyList and Check for collision between each enemy and the player and
        // spells in spellList.

        
        // Update gameDisplay so that it's center is set to the new center of the player's 
        // game coordinates
    }

    public void pause() {
        gameLoop.stopLoop();
    }

//    private void postMatchInfo(boolean endRound) {
//        String matchInfo="&endRound="+endRound;
//        matchInfo+=playerHand.getMatchInfo();
//        matchInfo+=deskManager.getMatchInfo();
//        Log.d(logTag, "matchInfoToPost:"+matchInfo);
//        postMatchInfoTask task = new postMatchInfoTask(username,password,matchInfo);
//        es2.execute(task);
//    }
}
