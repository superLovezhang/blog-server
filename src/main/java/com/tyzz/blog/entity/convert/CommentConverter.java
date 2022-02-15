package com.tyzz.blog.entity.convert;

import com.tyzz.blog.entity.pojo.Comment;
import com.tyzz.blog.entity.vo.CommentAdminVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-02-14 17:27
 */
@Mapper
public interface CommentConverter {
    CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

    @Mapping(target = "pics", expression = "java(comment.getArrayPics())")
    CommentAdminVO comment2AdminVO(Comment comment);
}
