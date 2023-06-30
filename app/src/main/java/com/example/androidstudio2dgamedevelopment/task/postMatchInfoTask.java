package com.example.androidstudio2dgamedevelopment.task;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.Utils;

import java.io.IOException;

public class postMatchInfoTask implements Runnable{
    String logTag="postMatchInfoTask";
    String username;
    String password;
    String matchInfo;
    public postMatchInfoTask(String username, String password,String matchInfo) {
        this.username=username;
        this.password=password;
        this.matchInfo=matchInfo;
    }

    @Override
    public void run() {

        try {
            Log.d(logTag, "POST info: "+matchInfo);
            Utils.RestfulAPI(username,password,"POST","postMatchInfo",matchInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
