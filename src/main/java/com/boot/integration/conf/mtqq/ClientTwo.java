package com.boot.integration.conf.mtqq;

import java.util.Scanner;

public class ClientTwo {

    public static void main(String[] args) {
        MqttConfig mqttConfig = new MqttConfig();
        mqttConfig.setUri("tcp://127.0.0.1:61613");
        mqttConfig.setClientId("clientTwo");
        mqttConfig.setUsername("admin");
        mqttConfig.setPassword("password");

        String[] topic = {"two"};

        MyMqttClient myMqttClient = new MyMqttClient(mqttConfig, topic);

        myMqttClient.connect();

        Scanner in = new Scanner(System.in);

        while (true) {
            myMqttClient.sendMsg("one", in.nextLine());
        }
    }
}
