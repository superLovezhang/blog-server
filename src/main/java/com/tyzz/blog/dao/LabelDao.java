package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyzz.blog.entity.pojo.Label;

import java.util.List;

/**
 * (Label)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public interface LabelDao extends BaseMapper<Label> {
    List<Label> hotList();
}