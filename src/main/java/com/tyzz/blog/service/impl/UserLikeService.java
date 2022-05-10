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
     * @param articleId 文章id
     * @return 用户id列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Long> findAllUserIdsByArticle(Long articleId) {
        return userLikeDao.findAllUserIdsByArticle(articleId);
    }

    /**
     * 通过评论获取所有点赞用户id
     * @param commentId 评论
     * @return 用户id列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Long> findAllUserIdsByComment(Long commentId) {
        return userLikeDao.findAllUserIdsByComment(commentId);
    }

    public long count(@NonNull Long id, String likeKey, LikeType likeType) {
        commonService.initCountData(id, dynamicSelectFunc(likeType), likeKey);
        return redisService.sGetSetSize(likeKey) - 1;
    }

    private Function<Long, List<Long>> dynamicSelectFunc(LikeType likeType) {
        if (likeType.equals(LikeType.ARTICLE)) {
            return userLikeDao::findAllUserIdsByArticle;
        } else {
            return userLikeDao::findAllUserIdsByComment;
        }
    }

    /**
     * 创建一个UserLike对象 通过articleId
     * @param articleId 文章id
     * @param userId 用户id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserLike createByArticleId(@NonNull Long articleId, @NonNull Long userId) {
        return UserLike.builder()
                .articleId(articleId)
                .userId(userId)
                .build();
    }

    /**
     * 创建一个UserLike对象 通过commentId
     * @param commentId 评论id
     * @param userId 用户id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserLike createByCommentId(@NonNull Long commentId, @NonNull Long userId) {
        return UserLike.builder()
                .commentId(commentId)
                .userId(userId)
                .build();
    }

    public void save(@NonNull UserLike userLike) {
        userLikeDao.insert(userLike);
    }

    /**
     * 删除文章点赞
     * @param articleId 文章id
     * @param userId 用户id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteArticleLike(@NonNull Long articleId, @NonNull Long userId) {
        QueryWrapper<UserLike> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId).eq("user_id", userId);
        userLikeDao.delete(wrapper);
    }

    /**
     * 删除评论点赞
     * @param commentId 评论id
     * @param userId 用户id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCommentLike(@NonNull Long commentId, @NonNull Long userId) {
        QueryWrapper<UserLike> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", commentId).eq("user_id", userId);
        userLikeDao.delete(wrapper);
    }
}