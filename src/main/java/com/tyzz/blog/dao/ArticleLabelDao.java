package com.tyzz.blog.dao;

import com.tyzz.blog.entity.ArticleLabel;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (ArticleLabel)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-05 23:05:22
 */
public interface ArticleLabelDao {

    /**
     * 通过ID查询单条数据
     *
     * @param articleLabelId 主键
     * @return 实例对象
     */
    ArticleLabel queryById(Long articleLabelId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ArticleLabel> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param articleLabel 实例对象
     * @return 对象列表
     */
    List<ArticleLabel> queryAll(ArticleLabel articleLabel);

    /**
     * 新增数据
     *
     * @param articleLabel 实例对象
     * @return 影响行数
     */
    int insert(ArticleLabel articleLabel);

    /**
     * 修改数据
     *
     * @param articleLabel 实例对象
     * @return 影响行数
     */
    int update(ArticleLabel articleLabel);

    /**
     * 通过主键删除数据
     *
     * @param articleLabelId 主键
     * @return 影响行数
     */
    int deleteById(Long articleLabelId);

}