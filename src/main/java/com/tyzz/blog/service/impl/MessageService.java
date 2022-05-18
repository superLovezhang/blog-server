package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.blog.dao.MessageDao;
import com.tyzz.blog.entity.pojo.Message;
import com.tyzz.blog.entity.pojo.Notification;
import com.tyzz.blog.enums.MessageStatus;
import com.tyzz.blog.exception.BlogException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (Message)表服务接口
 *
 * @author ZhangZhao
 * @since 2022-05-16 13:10:16
 */
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageDao messageDao;
    private final ObjectMapper mapper;

    /**
     * 通过message id查找对应message
     * @param id 消息id
     * @return message
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Message selectOneById(Long id) {
        return messageDao.selectById(id);
    }

    /**
     * 保存消息实体类
     * @param message 消息实体类
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Message saveMessage(Message message) {
        messageDao.insert(message);
        return message;
    }

    /**
     * 根据实体类id修改数据行
     * @param message 实体类消息
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Message updateMessage(Message message) {
        messageDao.updateById(message);
        return message;
    }

    /**
     * 根据重试次数获取limit数量的失败消息
     * @param limit 获取数量
     * @param maximumRetry 允许的最大重试次数
     * @return 失败消息列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Message> fetchFailureMessage(Integer limit, Integer maximumRetry) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.le("retry_count", maximumRetry)
                .eq("message_status", MessageStatus.SENT_ERROR.getValue())
                .last("limit " + limit);
        return messageDao.selectList(wrapper);
    }

    /**
     * 批量更新messages
     * @param messages 消息列表
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void batchUpdate(List<Message> messages) {
        for (Message message : messages) {
            messageDao.updateById(message);
        }
    }

    public Message create(Notification notification, String exchange, String routingKey) {
        try {
            return Message.builder()
                    .content(mapper.writeValueAsString(notification))
                    .toExchange(exchange)
                    .routingKey(routingKey)
                    .messageStatus(MessageStatus.NEW)
                    .build();
        } catch (JsonProcessingException e) {
            throw new BlogException("创建消息失败");
        }
    }
}