package com.tyzz.blog.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Label)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public class Label implements Serializable {
    private static final long serialVersionUID = 430509069866310502L;
    
    private Long labelId;
    
    private String labelName;
    
    private Byte state;
    
    private Date createTime;
    
    private Date updateTime;


    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}