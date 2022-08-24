package com.tyzz.blog.schedule;

import com.tyzz.blog.entity.pojo.Message;
import com.tyzz.blog.enums.MessageStatus;
import com.tyzz.blog.service.impl.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static com.tyzz.blog.constant.MqConstant.DEFAULT_FETCH_FAILURE_COUNT;
import static com.tyzz.blog.constant.MqConstant.DEFAULT_MAXIMUM_RETRY;

/**
 * Description:
 * 消息队列定时任务
 * @Author: ZhangZhao
 * DateTime: 2022-05-18 9:15
 */
@RequiredArgsConstructor
//@Component
public class MqSchedule {
    private final MessageService messageService;

    private final RabbitTemplate rabbitTemplate;

    /**
     * 每5分钟从数据库中取出发送失败的消息
     * 进行重新发送
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void consumerMessage() {
        retryTopMessages();
    }

    /**
     * 重新发送一定数量失败消息
     */
    private void retryTopMessages() {
        List<Message> messages = messageService.fetchFailureMessage(DEFAULT_FETCH_FAILURE_COUNT, DEFAULT_MAXIMUM_RETRY);
        if (messages.size() != 0) {
            for (Message message : messages) {
                rabbitTemplate.convertAndSend(message.getToExchange(), message.getRoutingKey(), message.getContent());
                message.incrementRetry();
                message.setMessageStatus(MessageStatus.SENT);
            }
            messageService.batchUpdate(messages);
        }
    }
}
