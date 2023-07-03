package com.example.androidstudio2dgamedevelopment.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

public class MatchTask implements Runnable{

    String logTag="MatchTask";

    Handler creator;
    String username;
    String password;

    public MatchTask(Handler handler,String username,String password){
        creator=handler;
        this.username=username;
        this.password=password;
    }

    @Override
    public void run() {
        Message msg;
        Bundle msg_data;
        int i=0;
//        int matchNumber=-1;

//        try {
//            setWaiting();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        while(matchNumber==-1) {
//            try {
//                try {
//                    matchNumber=findMatch();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
        while(true) {
            try {
                Thread.sleep(200); // sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            msg = creator.obtainMessage();
            msg_data = msg.getData(); // message data
//            msg_data.putInt("match",matchNumber); // (key, value = progress)
            msg_data.putInt("progress", i+1); // (key, value = progress)
            msg.sendToTarget();
            i++;
            if(i>20)i=0;
        }

    }
//    private void setWaiting()throws IOException{
//        HttpURLConnection connection = (HttpURLConnection) new URL("http://20.160.58.77:8080/Server/action/"+username).openConnection();
//
//        String authString = username + ":" + password;
//        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());
//        String authHeader = "Basic " + encodedAuth;
//        connection.setRequestProperty("Authorization", authHeader);
//
//        connection.setRequestMethod("POST");
//
//        String postData="command="+"waiting";
//
//        connection.setDoOutput(true);
//        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
//        wr.write(postData);
//        wr.flush();
//
//
//        int code=connection.getResponseCode();
//        Log.d(logTag, "after getResponseCode, code="+code);
//        if(code==200){
//            Log.d(logTag, "POST successful");
//        }else if(code==401){
//            Log.d(logTag, "Wrong password");
//        }
//    }
//    private int findMatch() throws IOException {
//        HttpURLConnection connection = (HttpURLConnection) new URL("http://20.160.58.77:8080/Server/action/"+username+"/match").openConnection();
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
//                return jsonObject.getInt("match");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        // an error happened
//        return -2;
//    }
}
