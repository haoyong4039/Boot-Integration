package com.boot.integration.conf.mtqq.handle;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @desc mqtt消息处理
 */
public interface IMqttMsgHandler
{
    void parseMsg(String topic, MqttMessage message);
}
