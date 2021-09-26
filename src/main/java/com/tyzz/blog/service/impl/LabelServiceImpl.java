package com.tyzz.blog.service.impl;

import com.tyzz.blog.dao.LabelDao;
import com.tyzz.blog.service.LabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Label)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("labelService")
public class LabelServiceImpl implements LabelService {
    @Resource
    private LabelDao labelDao;

    /**
     * 通过ID查询单条数据
     *
     * @param labelId 主键
     * @return 实例对象
     */
    @Override
    public Label queryById(Long labelId) {
        return this.labelDao.queryById(labelId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Label> queryAllByLimit(int offset, int limit) {
        return this.labelDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param label 实例对象
     * @return 实例对象
     */
    @Override
    public Label insert(Label label) {
        this.labelDao.insert(label);
        return label;
    }

    /**
     * 修改数据
     *
     * @param label 实例对象
     * @return 实例对象
     */
    @Override
    public Label update(Label label) {
        this.labelDao.update(label);
        return this.queryById(label.getLabelId());
    }

    /**
     * 通过主键删除数据
     *
     * @param labelId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long labelId) {
        return this.labelDao.deleteById(labelId) > 0;
    }
}