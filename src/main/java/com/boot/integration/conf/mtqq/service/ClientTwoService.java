package com.boot.integration.conf.mtqq.service;

import com.boot.integration.conf.mtqq.MqttConfig;
import com.boot.integration.conf.mtqq.MyMqttClient;
import com.boot.integration.conf.mtqq.handle.impl.ClientTwoHandler;
import org.springframework.stereotype.Service;

@Service
public class ClientTwoService
{
    private MyMqttClient myMqttClient;

    public void start()
    {
        MqttConfig mqttConfig = new MqttConfig();
        mqttConfig.setUri("tcp://127.0.0.1:61613");
        mqttConfig.setClientId("clientTwo");
        mqttConfig.setUsername("admin");
        mqttConfig.setPassword("password");

        String[] topic = {"two"};

        myMqttClient = new MyMqttClient(mqttConfig, topic, new ClientTwoHandler());

        myMqttClient.connect();
    }

    public void send(String topic, String message)
    {
        myMqttClient.sendMsg(topic, message);
    }
}
