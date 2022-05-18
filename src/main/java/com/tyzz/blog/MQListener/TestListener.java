package com.tyzz.blog.MQListener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-16 14:19
 */
@RabbitListener
@Service
public class TestListener {
    @RabbitHandler
    public void receiveMessage(Message message, Object object, Channel channel) {
//        message.getHeaders().
        // 消息签收确认
//        channel.basicAck();
        // 消息拒绝
//        channel.basicNack();
    }
}
