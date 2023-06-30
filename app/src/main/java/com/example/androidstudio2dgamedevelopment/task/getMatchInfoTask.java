package com.example.androidstudio2dgamedevelopment.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.androidstudio2dgamedevelopment.Utils;

import java.io.IOException;

public class getMatchInfoTask implements Runnable{
    String logTag="getMatchInfoTask";

    Handler creator;
    String username;
    String password;

    public getMatchInfoTask(Handler handler, String username, String password){
        creator=handler;
        this.username=username;
        this.password=password;
    }

    @Override
    public void run() {
        Message msg;
        Bundle msg_data;
        while(true){
            try {
                msg = creator.obtainMessage();
                msg_data = msg.getData(); // message data
                if(msg_data!=null)msg_data.putString("matchInfo", Utils.RestfulAPI(username,password,"GET","/getMatchInfo",""));
                msg.sendToTarget();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

//    private int postMatch() throws IOException {
//        HttpURLConnection connection = (HttpURLConnection) new URL("http://20.160.58.77:8080/Server/action/"+username+"/matchInfo").openConnection();
//
//        String authString = username + ":" + password;
//        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());
//        String authHeader = "Basic " + encodedAuth;
//        connection.setRequestProperty("Authorization", authHeader);
//
//        connection.setRequestMethod("GET");
//
//        int code=connection.getResponseCode();
//        if(code==200){StringBuilder response = new StringBuilder();
//            Scanner scanner = new Scanner(connection.getInputStream());
//            while (scanner.hasNextLine()) {
//                response.append(scanner.nextLine());
//                response.append("\n");
//            }
//            scanner.close();
//            JSONObject jsonObject;
//            try{
//                jsonObject=new JSONObject(response.toString());
//                Log.d(logTag, jsonObject.toString());
//                return jsonObject.getInt("addHand");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return 0;
//    }


}
