package com.tyzz.blog.service.impl;

import com.tyzz.blog.dao.LabelDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Label)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("labelService")
public class LabelService {
    @Resource
    private LabelDao labelDao;

}