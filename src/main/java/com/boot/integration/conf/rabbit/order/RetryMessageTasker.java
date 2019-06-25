//package com.boot.integration.conf.rabbit.order;
//
//import com.boot.integration.constant.Constants;
//import com.boot.integration.mapper.BrokerMessageLogMapper;
//import com.boot.integration.model.BrokerMessageLog;
//import com.boot.integration.model.Order;
//import com.boot.integration.util.data.ObjectMapperUtils;
//import org.aspectj.weaver.ast.Or;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * 定时重复投递
// */
//@Component
//public class RetryMessageTasker
//{
//
//    private static Logger logger = LoggerFactory.getLogger(RetryMessageTasker.class);
//
//    @Autowired
//    private RabbitOrderSender rabbitOrderSender;
//
//    @Autowired
//    private BrokerMessageLogMapper brokerMessageLogMapper;
//
//    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
//    public void reSend()
//    {
//        logger.info("-----------定时任务开始-----------");
//
//        // 查询消息状态为0(发送中) 且已经超时的消息集合
//        List<BrokerMessageLog> list = brokerMessageLogMapper.queryStatusAndTimeoutMessage();
//
//        logger.info("MsgResendList:{}",list);
//
//        list.forEach(messageLog -> {
//            if (messageLog.getTryCount() >= 3)
//            {
//                // 重复投递三次以上的消息状态设置为失败
//                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constants.ORDER_SEND_FAILURE, new Date());
//            }
//            else
//            {
//                try
//                {
//                    // 重复投递
//                    brokerMessageLogMapper.updateReSend(messageLog.getMessageId(), new Date());
//                    Order reSendOrder = (Order)ObjectMapperUtils.convertStringToObject(messageLog.getMessage(), Order.class);
//
//                    rabbitOrderSender.sendOrder(reSendOrder);
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}
//
