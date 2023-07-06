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

public class MatchTask implements Runnable {

    String logTag = "MatchTask";

    Handler creator;

    public MatchTask(Handler handler) {
        creator = handler;
    }

    @Override
    public void run() {
        Message msg;
        Bundle msg_data;
        int i = 0;
        while (true) {
            try {
                Thread.sleep(200); // sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            msg = creator.obtainMessage();
            msg_data = msg.getData(); // message data
//            msg_data.putInt("match",matchNumber); // (key, value = progress)
            msg_data.putInt("progress", i + 1); // (key, value = progress)
            msg.sendToTarget();
            i++;
            if (i > 20) i = 0;
        }

    }
}