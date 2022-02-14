package com.tyzz.blog.entity.convert;

import com.tyzz.blog.entity.pojo.Article;
import com.tyzz.blog.entity.vo.ArticleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-02-14 15:43
 */
@Mapper
public interface ArticleConverter {
    ArticleConverter INSTANCE = Mappers.getMapper(ArticleConverter.class);

    @Mapping(target = "status", source = "status")
    ArticleVO article2VO(Article article);
}
