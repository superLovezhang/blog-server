package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.dao.UserLikeDao;
import com.tyzz.blog.entity.pojo.Article;
import com.tyzz.blog.entity.pojo.Comment;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.pojo.UserLike;
import com.tyzz.blog.enums.LikeType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

/**
 * (CommentUser)表服务实现类
 *
 * @author makejava
 * @since 2021-10-14 20:48:50
 */
@Service("userLikeService")
@RequiredArgsConstructor
public class UserLikeService {
    private final UserLikeDao userLikeDao;
    private final RedisService redisService;
    private final CommonService commonService;

    @Transactional(propagation = Propagation.SUPPORTS)
    public UserLike findOneByCommentAndUser(Comment comment, User user) {
        if (user == null) {
            return null;
        }
        QueryWrapper<UserLike> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", comment.getCommentId());
        wrapper.eq("user_id", user.getUserId());
        return userLikeDao.selectOne(wrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserLike create(Comment comment, User user) {
        UserLike build = UserLike.builder()
                .commentId(comment.getCommentId())
                .userId(user.getUserId())
                .build();
        userLikeDao.insert(build);
        return build;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserLike create(Article article, User user) {
        UserLike build = UserLike.builder()
                .articleId(article.getArticleId())
                .userId(user.getUserId())
                .build();
        userLikeDao.insert(build);
        return build;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(UserLike userLike) {
        userLikeDao.deleteById(userLike.getUserLikeId());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int countByComment(Comment comment) {
        QueryWrapper<UserLike> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", comment.getCommentId());
        return userLikeDao.selectCount(wrapper);
    }

    /**
     * 通过文章获取所有点赞用户id
     * @param article 文章
     * @return 用户id列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Long> findAllUserIdsByArticle(Article article) {
        return userLikeDao.findAllUserIdsByArticle(article.getArticleId());
    }

    /**
     * 通过评论获取所有点赞用户id
     * @param comment 评论
     * @return 用户id列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Long> findAllUserIdsByComment(Comment comment) {
        return userLikeDao.findAllUserIdsByComment(comment.getCommentId());
    }

    public long count(@NonNull Long id, String likeKey, LikeType likeType) {
        commonService.initCountData(id, dynamicSelectFunc(likeType), likeKey);
        return redisService.sGetSetSize(likeKey);
    }

    private Function<Long, List<Long>> dynamicSelectFunc(LikeType likeType) {
        if (likeType.equals(LikeType.ARTICLE)) {
            return userLikeDao::findAllUserIdsByArticle;
        } else {
            return userLikeDao::findAllUserIdsByComment;
        }
    }
}