package com.example.androidstudio2dgamedevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.androidstudio2dgamedevelopment.task.LoginTask;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
    String username;
    String password;

    Handler hand_mqtt_msg = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMsg){

            // periodic temp check
            //int     temp = inputMsg.getData().getInt("temp");

            // handle mqtt receptions
            String topic = inputMsg.getData().getString("topic_name");
            String payload = inputMsg.getData().getString("payload");
            System.out.println("received LOGIN:" +topic+payload);
            if(topic.equals("STC/"+username+"/logged")&& payload.equals("logged")){
                Toast.makeText(getApplicationContext(),"Hello, "+username ,Toast.LENGTH_SHORT).show();
                mqtt_handler.disconnectToBroker();
                Intent intent = new Intent( LoginActivity.this, MatchActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
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
    //ExecutorService es;

    MQTTModule mqtt_handler = MQTTModule.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mqtt_handler.setHandler(hand_mqtt_msg);


        editUsername = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);
//        handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message inputMessage) {
//                String topic = inputMessage.getData().getString("topic");
//                String payload = inputMessage.getData().getString("payload");
//                if(payload != null){
//                    mqtt_handler.connectToBroker("tcp://52.148.250.153:1883");
//                }
//                Log.d("login","topic"+topic+"p:"+payload);
//                if(topic.equals("STC/"+username+"/logged")&& payload.equals("logged")){
//                    Toast.makeText(getApplicationContext(),"Hello, "+username ,Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent( LoginActivity.this, MatchActivity.class);
//                    intent.putExtra("username", username);
//                    intent.putExtra("password", password);
//                    startActivity(intent);
//
//                }else  {
//                    Toast.makeText(getApplicationContext(),"Wrong username or password, try again.",Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
    }

    public void loginPressed(View view) {
        username=editUsername.getText().toString();
        password=editPassword.getText().toString();

//        if(!mqtt_handler.isConnected) {
//            if(mqtt_handler.connectToBroker("tcp://52.148.250.153:1883")){
//                mqtt_handler.subscribeToTopic("STC/"+username+"/#");
//            }
//        }
        if(mqtt_handler.connectToBroker("tcp://52.148.250.153:1883")){
            mqtt_handler.subscribeToTopic("STC/#");
        }
        mqtt_handler.sendMSGToTopic("CTS/"+username+"/login","login",2);

    }


}
