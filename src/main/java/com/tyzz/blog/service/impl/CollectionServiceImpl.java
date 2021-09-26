package com.tyzz.blog.service.impl;

import com.tyzz.blog.dao.CollectionDao;
import com.tyzz.blog.entity.Collection;
import com.tyzz.blog.service.CollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Collection)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {
    @Resource
    private CollectionDao collectionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param collectionId 主键
     * @return 实例对象
     */
    @Override
    public Collection queryById(Long collectionId) {
        return this.collectionDao.queryById(collectionId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Collection> queryAllByLimit(int offset, int limit) {
        return this.collectionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param collection 实例对象
     * @return 实例对象
     */
    @Override
    public Collection insert(Collection collection) {
        this.collectionDao.insert(collection);
        return collection;
    }

    /**
     * 修改数据
     *
     * @param collection 实例对象
     * @return 实例对象
     */
    @Override
    public Collection update(Collection collection) {
        this.collectionDao.update(collection);
        return this.queryById(collection.getCollectionId());
    }

    /**
     * 通过主键删除数据
     *
     * @param collectionId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long collectionId) {
        return this.collectionDao.deleteById(collectionId) > 0;
    }
}