package com.tyzz.blog.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-27 21:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionVO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long collectionId;

    private ArticleVO article;

    private UserVO user;
}
