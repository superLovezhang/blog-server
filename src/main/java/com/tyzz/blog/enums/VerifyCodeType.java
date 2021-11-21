package com.tyzz.blog.enums;

import com.tyzz.blog.constant.BlogConstant;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-11-21 12:39
 */
public enum VerifyCodeType {
    REGISTER(0, BlogConstant.REGISTER_VERIFY_PREFIX, BlogConstant.EMAIL_VERIFICATION_SUBJECT, BlogConstant.EMAIL_VERIFICATION_TEXT),
    UPDATE_PWD(1, BlogConstant.PWD_VERIFY_PREFIX, BlogConstant.EMAIL_PWD_SUBJECT, BlogConstant.EMAIL_PWD_TEXT);

    private Integer number;
    private String code;
    private String subject;
    private String text;

    public static String getCodeByNumber(Integer number) {
        for (VerifyCodeType value : VerifyCodeType.values()) {
            if (value.number.equals(number)) {
                return value.code;
            }
        }
        return BlogConstant.REGISTER_VERIFY_PREFIX;
    }

    public static VerifyCodeType getInstanceByNumber(Integer number) {
        for (VerifyCodeType value : VerifyCodeType.values()) {
            if (value.number.equals(number)) {
                return value;
            }
        }
        return REGISTER;
    }

    VerifyCodeType(Integer number, String code, String subject, String text) {
        this.number = number;
        this.code = code;
        this.subject = subject;
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
