package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.dao.MessageDao;
import com.tyzz.blog.entity.pojo.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final JdbcTemplate jdbcTemplate;

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
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveMessage(Message message) {
        messageDao.updateById(message);
    }

    /**
     * 根据重试次数获取limit数量的失败消息
     * @param limit 获取数量
     * @param maximumRetry 允许的最大重试次数
     * @return 失败消息列表
     */
    public List<Message> fetchFailureMessage(Integer limit, Integer maximumRetry) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("retry_count", maximumRetry)
                .last("limit " + limit);
        return messageDao.selectList(wrapper);
    }

    public void batchUpdate(List<Message> messages) {
        for (Message message : messages) {
            messageDao.updateById(message);
        }
    }
}