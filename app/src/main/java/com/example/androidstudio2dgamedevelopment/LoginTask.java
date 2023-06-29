package com.example.androidstudio2dgamedevelopment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

public class LoginTask implements Runnable{

    Handler creator;
    String username;
    String password;

    public LoginTask(Handler handler,String username,String password){
        creator=handler;
        this.username=username;
        this.password=password;
    }

    @Override
    public void run() {
        Message msg;
        Bundle msg_data;
        boolean result;
        try {
            result=login(username,password);
            msg = creator.obtainMessage();
            msg_data = msg.getData(); // message data
            msg_data.putBoolean("login",result); // (key, value = progress)
            msg.sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean login(String username, String password) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://20.160.58.77:8080/Server/action/"+username).openConnection();



        String authString = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());
        String authHeader = "Basic " + encodedAuth;
        connection.setRequestProperty("Authorization", authHeader);

        connection.setRequestMethod("GET");

        int code=connection.getResponseCode();
        if(code==200){StringBuilder response = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
                response.append("\n");
            }
            scanner.close();
            JSONObject jsonObject;
            try{
                jsonObject=new JSONObject(response.toString());
                if(jsonObject.getString("name").equals(username)) return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        // an error happened
        return false;
    }
}
