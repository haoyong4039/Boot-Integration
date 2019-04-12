package com.boot.integration.service.impl;

import com.boot.integration.conf.rabbit.RabbitOrderSender;
import com.boot.integration.constant.Constants;
import com.boot.integration.mapper.BrokerMessageLogMapper;
import com.boot.integration.mapper.OrderMapper;
import com.boot.integration.model.BrokerMessageLog;
import com.boot.integration.model.Order;
import com.boot.integration.util.data.ObjectMapperUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService
{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    public void createOrder(Order order) throws Exception
    {
        // 插入业务数据
        orderMapper.insert(order);

        // 插入消息记录表数据
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        // 消息唯一ID
        brokerMessageLog.setMessageId(order.getMessageId());
        // 保存消息整体 转为JSON 格式存储入库
        brokerMessageLog.setMessage(ObjectMapperUtils.convertObjectToString(order));
        // 设置消息状态为0 表示发送中
        brokerMessageLog.setStatus("0");
        // 设置消息未确认超时时间窗口为 一分钟
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(new Date(), Constants.ORDER_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLogMapper.insertSelective(brokerMessageLog);

        // 发送消息
        rabbitOrderSender.sendOrder(order);
    }

}

