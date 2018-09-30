package com.boot.integration.conf.mtqq.client;

import com.boot.integration.conf.mtqq.MqttConfig;
import com.boot.integration.conf.mtqq.MyMqttClient;
import com.boot.integration.conf.mtqq.handle.impl.ClientOneHandler;
import org.springframework.stereotype.Service;

@Service
public class ClientOneService
{
    private MyMqttClient myMqttClient;

    public void start()
    {
        MqttConfig mqttConfig = new MqttConfig();
        mqttConfig.setUri("tcp://127.0.0.1:61613");
        mqttConfig.setClientId("clientOne");
        mqttConfig.setUsername("admin");
        mqttConfig.setPassword("password");

        String[] topic = {"one"};

        myMqttClient = new MyMqttClient(mqttConfig, topic, new ClientOneHandler());

        myMqttClient.connect();
    }

    public void send(String topic, String message)
    {
        myMqttClient.sendMsg(topic, message);
    }
}
