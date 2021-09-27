package com.tyzz.blog.entity.enums;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-26 12:33
 */
public enum ResponseCode {
    /**
     * 1000-1999 通用成功失败
     * 2000-2999 登录相关
     * 3000-3999 权限相关
     */
    SUCCESS("操作成功", 1000L),
    FAIL("操作失败", 1001L),
    LOGIN_SUCCESS("登陆成功", 2000L),
    LOGIN_FAIL("登陆失败", 2001L),
    PERMISSION_FAIL("暂无权限", 3000L)
    ;

    private String desc;
    private Long code;

    ResponseCode(String desc, Long code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public Long getCode() {
        return code;
    }
}
