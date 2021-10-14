package com.tyzz.blog.entity.dao;

import com.tyzz.blog.entity.entity.CommentUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (CommentUser)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-14 20:49:22
 */
public interface CommentUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param commentUserId 主键
     * @return 实例对象
     */
    CommentUser queryById(Long commentUserId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CommentUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param commentUser 实例对象
     * @return 对象列表
     */
    List<CommentUser> queryAll(CommentUser commentUser);

    /**
     * 新增数据
     *
     * @param commentUser 实例对象
     * @return 影响行数
     */
    int insert(CommentUser commentUser);

    /**
     * 修改数据
     *
     * @param commentUser 实例对象
     * @return 影响行数
     */
    int update(CommentUser commentUser);

    /**
     * 通过主键删除数据
     *
     * @param commentUserId 主键
     * @return 影响行数
     */
    int deleteById(Long commentUserId);

}