package com.tyzz.blog.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Label)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public interface LabelDao {

    /**
     * 通过ID查询单条数据
     *
     * @param labelId 主键
     * @return 实例对象
     */
    Label queryById(Long labelId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Label> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param label 实例对象
     * @return 对象列表
     */
    List<Label> queryAll(Label label);

    /**
     * 新增数据
     *
     * @param label 实例对象
     * @return 影响行数
     */
    int insert(Label label);

    /**
     * 修改数据
     *
     * @param label 实例对象
     * @return 影响行数
     */
    int update(Label label);

    /**
     * 通过主键删除数据
     *
     * @param labelId 主键
     * @return 影响行数
     */
    int deleteById(Long labelId);

}