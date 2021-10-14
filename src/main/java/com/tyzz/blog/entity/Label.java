package com.tyzz.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Label)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:51:06
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label implements Serializable {
    private static final long serialVersionUID = -46878161881989582L;

    @TableId
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long labelId;

    private String labelName;
    
    private Boolean state;
    
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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
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