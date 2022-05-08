package com.tyzz.blog.service;

import com.tyzz.blog.entity.pojo.User;

/**
 * Description:
 * 点赞顶级抽象接口
 * @Author: ZhangZhao
 * DateTime: 2022-05-08 9:20
 */
public interface ILike {
    /**
     * 通用点赞/取消点赞方法
     * @param id 被点赞/取消事物id
     * @param user 点赞/取消用户
     */
    void like(Long id, User user);

}
