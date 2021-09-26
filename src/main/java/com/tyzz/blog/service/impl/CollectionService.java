package com.tyzz.blog.service.impl;

import com.tyzz.blog.dao.CollectionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Collection)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("collectionService")
public class CollectionService {
    @Resource
    private CollectionDao collectionDao;


}