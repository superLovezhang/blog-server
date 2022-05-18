package com.tyzz.blog.MQListener;

import com.rabbitmq.client.Channel;
import com.tyzz.blog.entity.pojo.Notification;
import com.tyzz.blog.service.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.tyzz.blog.constant.MqConstant.ARTICLE_AUDIT_QUEUE;

/**
 * Description:
 * 监听文章审核队列
 * @Author: ZhangZhao
 * DateTime: 2022-05-16 14:19
 */
@Service
@RabbitListener(queues = ARTICLE_AUDIT_QUEUE)
@RequiredArgsConstructor
public class ArticleAuditListener {
    private final NotificationService notificationService;

    @RabbitHandler
    public void receiveMessage(Message message, Notification notification, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            notificationService.save(notification);
            // 消息签收确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 消息拒绝
            channel.basicNack(deliveryTag, false, false);
        }
    }
}

