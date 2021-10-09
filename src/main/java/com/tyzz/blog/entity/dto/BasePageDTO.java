package com.tyzz.blog.entity.dto;

import com.tyzz.blog.util.StringUtils;
import lombok.Data;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 15:20
 */
@Data
public class BasePageDTO {
    private Integer page = 1;
    private Integer size = 10;
    private String sortColumn = "createTime";
    private String underscoreSortColumn;

    public String getUnderscoreSortColumn() {
        return StringUtils.camelToUnderscore(this.sortColumn);
    }
}
