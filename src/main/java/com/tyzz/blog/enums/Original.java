package com.tyzz.blog.enums;

import java.lang.annotation.*;

/**
 * 添加在接口方法上表示当前响应结果无需包裹处理
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Original {
}
