package com.example.androidstudio2dgamedevelopment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MyMQTT {
    final String serverUri = "tcp://52.148.250.153:1883";
    String subscriptionTopic;

    MqttAndroidClient mqttAndroidClient;
    Handler context_handler;
    String clientId = "HDIGame";
    Message msg;
    Bundle  data;

    public void setHandler(Handler hand){
        context_handler = hand;
    }

    public  MyMQTT(Context context) {



        clientId = clientId + System.currentTimeMillis();

        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                if (reconnect) {
                     Log.d("MQTT","Reconnected to : " + serverURI);
                    // Because Clean Session is true, we need to re-subscribe
                } else {
                    Log.d("MQTT","Connected to: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d("MQTT","The Connection was lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                Log.d("MQTT","Incoming message: " + new String(message.getPayload()));
                msg = context_handler.obtainMessage();
                data = msg.getData();
                data.putString("topic", topic);
                data.putString("payload", new String(message.getPayload(), StandardCharsets.UTF_8));
                msg.sendToTarget();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        //mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setCleanSession(true);

        Log.d("MQTT","Connecting to " + serverUri + "...");
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("MQTT","Failed to connect to: " + serverUri +
                            ". Cause: " + ((exception.getCause() == null)?
                            exception.toString() : exception.getCause()));
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            Log.d("MQTT",e.toString());
        }
    }




    public void disconnect() {
        try {
            mqttAndroidClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
            Log.d("MQTT",e.toString());
        }
    }
    public void subscribeToTopic(String topic) {
        try {
            mqttAndroidClient.subscribe(topic, 2, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("MQTT","Subscribed to: " + topic);
                    subscriptionTopic=topic;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("MQTT","Failed to subscribe");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            Log.d("MQTT",e.toString());
        }

    }

    public void publishMessage(String topic,String payload) {
        MqttMessage message = new MqttMessage();
        message.setPayload(payload.getBytes());
        message.setRetained(false);
        message.setQos(2);
        try {
            mqttAndroidClient.publish(topic, message);
            Log.d("MQTT","Message Published");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MQTT",e.toString());
        }
        if (!mqttAndroidClient.isConnected()) {
            Log.d("MQTT","Client not connected!");
        }
    }
}
