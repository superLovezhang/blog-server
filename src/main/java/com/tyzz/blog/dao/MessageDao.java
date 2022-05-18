package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyzz.blog.entity.pojo.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Message)表数据库访问层
 *
 * @author ZhangZhao
 * @since 2022-05-16 13:10:16
 */
public interface MessageDao extends BaseMapper<Message> {
    void batchUpdate(@Param("messages") List<Message> messages);
}