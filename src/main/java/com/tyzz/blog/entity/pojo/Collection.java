package com.tyzz.blog.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * (Collection)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:51:06
 */
@Alias("BlogCollection")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collection extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -59113307433898013L;

    @TableId
    private Long collectionId;
    
    private Long articleId;
    
    private Long userId;

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}