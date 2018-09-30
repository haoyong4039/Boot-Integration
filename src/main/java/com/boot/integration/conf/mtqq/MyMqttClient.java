package com.boot.integration.conf.mtqq;

import com.boot.integration.conf.mtqq.handle.IMqttMsgHandler;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMqttClient
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //连接参数
    private MqttConfig mqttConfig;

    // 订阅主题
    private String[] topic;

    //mqtt客户端
    private MqttClient mqttClient;

    // MQTT的连接设置
    private MqttConnectOptions mqttConnectOptions;

    //订阅消息处理
    private IMqttMsgHandler iMqttMsgHandler;

    public MyMqttClient(MqttConfig mqttConfig, String[] topic, IMqttMsgHandler iMqttMsgHandler)
    {
        this.mqttConfig = mqttConfig;
        this.topic = topic;
        this.iMqttMsgHandler = iMqttMsgHandler;
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 连接服务
     * <功能详细描述>
     * </pre>
     */
    public void connect()
    {
        try
        {

            logger.info("params[config:{} - topic:{}]", mqttConfig.toString(), topic);

            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            mqttClient = new MqttClient(mqttConfig.getUri(), mqttConfig.getClientId(), new MemoryPersistence());

            // MQTT的连接设置
            mqttConnectOptions = new MqttConnectOptions();

            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
            mqttConnectOptions.setCleanSession(false);

            // 设置连接的用户名
            mqttConnectOptions.setUserName(mqttConfig.getUsername());

            // 设置连接的密码
            mqttConnectOptions.setPassword(mqttConfig.getPassword().toCharArray());

            // 设置超时时间 单位为秒
            mqttConnectOptions.setConnectionTimeout(mqttConfig.getConnectionTimeout());

            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            mqttConnectOptions.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());

            // 设置回调
            mqttClient.setCallback(new Callback(iMqttMsgHandler));

            // setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            if (StringUtils.isNotEmpty(mqttConfig.getWillTopic()) && StringUtils.isNotEmpty(mqttConfig.getWillMsg()))
            {
                mqttConnectOptions.setWill(mqttConfig.getWillTopic(), mqttConfig.getWillMsg().getBytes(), 2, true);
            }

            mqttClient.connect(mqttConnectOptions);

            if (topic.length > 0)
            {
                mqttClient.subscribe(topic, getQos(topic.length));

            }

            logger.info("init mqtt client success.config[{}]", mqttConfig.toString());

        }
        catch (Exception e)
        {
            logger.error("init mqtt client fail.config[{}]", mqttConfig.toString());
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 发送消息
     * <功能详细描述>
     * </pre>
     */
    public void sendMsg(String topic, String msg)
    {
        try
        {
            logger.info("params[topic:{} - msg:{}]", topic, msg);

            MqttMessage message = new MqttMessage();

            /**
             * 三种消息传输方式QoS：
             *
             * 0代表“至多一次”，消息发布完全依赖底层 TCP/IP 网络。会发生消息丢失或重复。这一级别可用于如下情况，环境传感器数据，丢失一次读记录无所谓，因为不久后还会有第二次发送。
             * 1代表“至少一次”，确保消息到达，但消息重复可能会发生。 2代表“只有一次”，确保消息到达一次。这一级别可用于如下情况，在计费系统中，消息重复或丢失会导致不正确的结果。
             * （备注：由于服务端采用Mosca实现，Mosca目前只支持到QoS 1）
             */
            message.setQos(1); // 保证消息能到达一次
            message.setRetained(true);
            message.setPayload(msg.getBytes());

            MqttDeliveryToken token = mqttClient.getTopic(topic).publish(message);
            token.waitForCompletion();

            logger.info("send msg success.params[topic:{} - msg:{}]", topic, msg);
        }
        catch (Exception e)
        {
            logger.error("send msg fail.params[topic:{} - msg:{}]", topic, msg);
            logger.error(topic, e);
        }

    }

    /**
     * <pre>
     * <一句话功能简述>
     * 根据topic获取qos
     * <功能详细描述>
     * </pre>
     */
    private int[] getQos(int topicCount)
    {
        int[] qos = new int[topicCount];

        for (int i = 0; i < topicCount; i++)
        {
            qos[i] = 1;
        }
        return qos;
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 消息处理内部类
     * <功能详细描述>
     * </pre>
     */
    class Callback implements MqttCallback
    {
        private IMqttMsgHandler iMqttMsgHandler;

        public Callback(IMqttMsgHandler iMqttMsgHandler)
        {
            this.iMqttMsgHandler = iMqttMsgHandler;
        }

        public void connectionLost(Throwable cause)
        {
            // 连接丢失后，一般在这里面进行重连
            logger.info("mqtt connection is lost");

            synchronized (this)
            {
                while (true)
                {
                    try
                    {
                        connect();
                        logger.info("mqtt reconnect success.config[{}]", mqttConfig.toString());

                        break;
                    }
                    catch (Exception e)
                    {
                        logger.error("{},mqtt reconnect fail.[{}]", mqttConfig.toString(), e);

                        try
                        {
                            Thread.sleep(5000);
                        }
                        catch (InterruptedException e1)
                        {
                        }
                    }
                }
            }
        }

        public void deliveryComplete(IMqttDeliveryToken token)
        {
            logger.info("deliveryComplete---------" + token.isComplete());
        }

        /**
         * <pre>
         * <一句话功能简述>
         * 接收消息处理
         * <功能详细描述>
         * </pre>
         */
        public void messageArrived(String topic, MqttMessage message)
        {
            // subscribe后得到的消息会执行到这里面
            iMqttMsgHandler.parseMsg(topic, message);
        }
    }
}
