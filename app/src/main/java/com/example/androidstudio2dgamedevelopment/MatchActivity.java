package com.example.androidstudio2dgamedevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.androidstudio2dgamedevelopment.task.MatchTask;

import java.sql.ClientInfoStatus;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatchActivity extends AppCompatActivity {

    String logTag="MatchActivity";

    String username;
    String password;

    ExecutorService es;
    Button buttonAsync;
    ProgressBar progressBar;
    Handler handler= new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message inputMessage) {
            int i = inputMessage.getData().getInt("progress", -1);
            Log.d(logTag, "Message Received with progress = " + i);
            progressBar.setProgress(i);
        }
    };;

    Handler hand_mqtt_msg = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMsg){

            // periodic temp check
            //int     temp = inputMsg.getData().getInt("temp");

            // handle mqtt receptions
            String topic = inputMsg.getData().getString("topic_name");
            String payload = inputMsg.getData().getString("payload");
            System.out.println("received game_name:" +topic+payload);
            if(topic.equals("STC/"+username+"/game_name")){
                Toast.makeText(getApplicationContext(),"Match:  "+payload ,Toast.LENGTH_SHORT).show();
                mqtt_handler.disconnectToBroker();
                Intent intent = new Intent( MatchActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("game_name", payload);
                startActivity(intent);

            }else  {
                Toast.makeText(getApplicationContext(),"Wrong username or password, try again.",Toast.LENGTH_SHORT).show();
            }
            // pop up manage
//            String msgs = inputMsg.getData().getString("pop_up");
//            if(msgs != null){
//                mqtt_handler.connectToBroker("tcp://52.148.250.153:1883");
//            }
        }
    };
    MQTTModule mqtt_handler = MQTTModule.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Intent intent = getIntent();
        username=intent.getStringExtra("username");
        password=intent.getStringExtra("password");

        es= Executors.newSingleThreadExecutor();

        mqtt_handler.setHandler(hand_mqtt_msg);


        buttonAsync = findViewById(R.id.buttonAsync);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(20);

        buttonAsync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(logTag, "Scheduling new task in background thread");
                if(mqtt_handler.connectToBroker("tcp://52.148.250.153:1883")){
                    mqtt_handler.subscribeToTopic("STC/"+username+"/#");
                }
                mqtt_handler.sendMSGToTopic("CTS/"+username+"/matching","matching",2);

                MatchTask task = new MatchTask(handler);
                es.execute(task);
            }
        });

    }


}
