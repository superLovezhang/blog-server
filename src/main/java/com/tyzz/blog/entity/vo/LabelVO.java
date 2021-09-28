package com.tyzz.blog.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 9:12
 */
@Data
public class LabelVO {
    private Long labelId;

    @NotBlank(message = "标签名称不能为空")
    private String labelName;
}
