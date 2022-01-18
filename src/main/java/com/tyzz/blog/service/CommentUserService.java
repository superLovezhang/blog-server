package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.dao.CommentUserDao;
import com.tyzz.blog.entity.pojo.Comment;
import com.tyzz.blog.entity.pojo.CommentUser;
import com.tyzz.blog.entity.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (CommentUser)表服务实现类
 *
 * @author makejava
 * @since 2021-10-14 20:48:50
 */
@Service("commentUserService")
public class CommentUserService {
    @Resource
    private CommentUserDao commentUserDao;

    public CommentUser findOneByCommentAndUser(Comment comment, User user) {
        if (user == null) {
            return null;
        }
        QueryWrapper<CommentUser> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", comment.getCommentId());
        wrapper.eq("user_id", user.getUserId());
        return commentUserDao.selectOne(wrapper);
    }

    public CommentUser create(Comment comment, User user) {
        CommentUser build = CommentUser.builder()
                .commentId(comment.getCommentId())
                .userId(user.getUserId())
                .build();
        commentUserDao.insert(build);
        return build;
    }

    public void remove(CommentUser commentUser) {
        commentUserDao.deleteById(commentUser.getCommentUserId());
    }

    public int countByComment(Comment comment) {
        QueryWrapper<CommentUser> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", comment.getCommentId());
        return commentUserDao.selectCount(wrapper);
    }
}