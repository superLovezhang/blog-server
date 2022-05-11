package com.tyzz.blog.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * 文章记录VO类
 * @Author: ZhangZhao
 * DateTime: 2022-05-11 15:14
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRecordVO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long articleId;

    private String articleName;
}
