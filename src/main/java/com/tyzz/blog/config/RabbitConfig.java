package com.tyzz.blog.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * RabbitMQ配置文件
 * @Author: ZhangZhao
 * DateTime: 2022-05-16 11:32
 */
@Configuration
public class RabbitConfig {
    /**
     * 默认使用jdk序列化serializable更换为json序列化
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 自己定制一个rabbitTemplate
     * @param connectionFactory rabbitMQ的连接工厂
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate(rabbitTemplate);
        return rabbitTemplate;
    }

    // todo 完善一下消息的可靠性投递
    private void initRabbitTemplate(RabbitTemplate rabbitTemplate) {
        // 设置message到达exchange的确认回调
        // correlationData消息的唯一id
        // ack消息是否被exchange成功收到
        // cause失败原因
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 每次发送一个消息都往数据库存一条记录 如果有exchange回调了true就改变对应记录状态
            if (ack) {

            } else {
                // 如果没有回调或者回调为false采用容错手段（重新发送、交给人工处理）
            }
        });
        // 设置message没有分配到queue的确认回调
        // message投递失败的消息
        // replyCode回复的状态码
        // replyText回复的文本内容
        // exchange消息投递的交换器
        // routingKey消息的路由键
        rabbitTemplate.setReturnsCallback(returned -> {
            String messageId = returned.getMessage().getMessageProperties().getMessageId();
            // 对于没有队列接收的消息 更改对于数据库记录行记录和原因
            // 等待下一次重新投递或者人工处理
        });
    }
}

