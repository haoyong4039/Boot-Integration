package com.boot.integration.conf.mtqq;

import java.util.Scanner;

public class ClientOne {

    public static void main(String[] args) {
        MqttConfig mqttConfig = new MqttConfig();
        mqttConfig.setUri("tcp://127.0.0.1:61613");
        mqttConfig.setClientId("clientOne");
        mqttConfig.setUsername("admin");
        mqttConfig.setPassword("password");

        String[] topic = {"one"};

        MyMqttClient myMqttClient = new MyMqttClient(mqttConfig, topic);

        myMqttClient.connect();

        Scanner in = new Scanner(System.in);

        while (true) {
            myMqttClient.sendMsg("two", in.nextLine());
        }
    }
}
