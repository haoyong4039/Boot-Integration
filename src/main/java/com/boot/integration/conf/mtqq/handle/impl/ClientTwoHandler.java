package com.boot.integration.conf.mtqq.handle.impl;

import com.boot.integration.conf.mtqq.handle.IMqttMsgHandler;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTwoHandler implements IMqttMsgHandler
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void parseMsg(String topic, MqttMessage message)
    {
        logger.info("handle:two - topic:{} - msg:{}", topic, new String(message.getPayload()));
    }
}
