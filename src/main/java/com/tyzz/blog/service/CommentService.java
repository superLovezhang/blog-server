package com.tyzz.blog.service;

import com.tyzz.blog.dao.CommentDao;
import com.tyzz.blog.entity.Comment;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.vo.CommentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("commentService")
public class CommentService {
    @Resource
    private CommentDao commentDao;


    public void comment(CommentVO commentVO, User user) {
        Comment comment = Comment.builder()
                .articleId(commentVO.getArticleId())
                .replyId(commentVO.getReplyId())
                .parentId(commentVO.getParentId())
                .content(commentVO.getContent())
                .userId(user.getUserId())
                .build();
        commentDao.insert(comment);
    }
}