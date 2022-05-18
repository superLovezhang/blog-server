package com.tyzz.blog.config.rabbitMQ.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tyzz.blog.constant.MqConstant.*;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-18 13:03
 */
@Configuration
public class ArticleExchange {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ARTICLE_NOTIFICATION_EXCHANGE, true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(ARTICLE_AUDIT_QUEUE, true, false, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(ARTICLE_AUDIT_KEY);
    }
}
