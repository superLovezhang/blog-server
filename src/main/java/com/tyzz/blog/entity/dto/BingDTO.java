package com.tyzz.blog.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-11 13:44
 */
@Data
public class BingDTO {
    private List<Object> images = new ArrayList<Object>();
    private Object tooltips;
}
