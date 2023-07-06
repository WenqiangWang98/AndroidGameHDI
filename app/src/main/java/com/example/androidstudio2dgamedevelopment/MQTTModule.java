package com.example.androidstudio2dgamedevelopment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MQTTModule {

    Message msg;
    Bundle  data;
    Handler context_handler;
    String clientId     = "HIDGameMobile";
    MemoryPersistence persistence = new MemoryPersistence();
    boolean isConnected = false;

    private static final MQTTModule handler = new MQTTModule();

//    String broker = "ee0cc4ea97e546c0aaa18d0f755f3328.s2.eu.hivemq.cloud:8883";
    MqttClient sampleClient;

    public static MQTTModule getInstance(){ return handler; }

    public void setHandler(Handler hand){
        context_handler = hand;
    }

    public void send_msg_to_context(String t, String msg_to_send){
        msg = context_handler.obtainMessage();
        data = msg.getData();
        data.putString("topic_name", t);
        data.putString("payload", msg_to_send);
        msg.sendToTarget();
    }

    private MQTTModule(){
        // connectToBrokerWithID();
        clientId = clientId + System.currentTimeMillis();
        //SubsManager = new SubscriptionManager();
    }



    public class MqttSubscriber implements MqttCallback {

        public void connectionLost(Throwable arg0){
            isConnected = false;
            //System.out.println("MQTT Connection lost");
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String t = topic;
            String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
            System.out.println("MQTT On message: " + t+", "+payload);
            send_msg_to_context(t, payload);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    }

    public boolean sendMSGToTopic(String topic, String content, int qos){
        if (isConnected){
            try {
                System.out.println("MQTT Publishing message: " + content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                sampleClient.publish(topic, message);
                System.out.println("Message published");
                return true;
            } catch(MqttException me) {
                System.out.println("reason "+me.getReasonCode());
                System.out.println("msg "+me.getMessage());
                System.out.println("loc "+me.getLocalizedMessage());
                System.out.println("cause "+me.getCause());
                System.out.println("excep "+me);
                me.printStackTrace();
            }
        }
        return false;
    }

    public boolean subscribeToTopic(String topic){
        if (isConnected){
            try {
                sampleClient.subscribe(topic);
                System.out.println("MQTT Subscript to "+topic);
                return true;
            } catch(MqttException me) {
                System.out.println("reason "+me.getReasonCode());
                System.out.println("msg "+me.getMessage());
                System.out.println("loc "+me.getLocalizedMessage());
                System.out.println("cause "+me.getCause());
                System.out.println("excep "+me);
                me.printStackTrace();
            }
        }
        return false;
    }

    public boolean connectToBroker(String broker){
        if (!isConnected){
            if (broker.length() > 0){
                try {
                    //broker = broker;
                    sampleClient = new MqttClient(broker, clientId, persistence);
                    MqttConnectOptions connOpts = new MqttConnectOptions();
                    connOpts.setAutomaticReconnect(true);
                    connOpts.setCleanSession(true);
                    connOpts.setConnectionTimeout(0);
                    System.out.println("Connecting to broker: "+broker);
                    sampleClient.setCallback(new MqttSubscriber());
                    sampleClient.connect(connOpts);
                    System.out.println("Connected");
                    isConnected = true;
                    return true;
                } catch(MqttException me) {
                    System.out.println("MQTT connect error:");
                    System.out.println("reason "+me.getReasonCode());
                    System.out.println("msg "+me.getMessage());
                    System.out.println("loc "+me.getLocalizedMessage());
                    System.out.println("cause "+me.getCause());
                    System.out.println("excep "+me);
                    me.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean disconnectToBroker() {
        if (isConnected){
            try {
                sampleClient.disconnect();
                isConnected = false;
                return true;
            }catch(MqttException me){
                System.out.println("reason "+me.getReasonCode());
                System.out.println("msg "+me.getMessage());
                System.out.println("loc "+me.getLocalizedMessage());
                System.out.println("cause "+me.getCause());
                System.out.println("excep "+me);
                me.printStackTrace();
            }

        }
        return false;
    }
}
