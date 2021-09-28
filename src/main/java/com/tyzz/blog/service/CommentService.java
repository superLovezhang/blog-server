package com.tyzz.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyzz.blog.dao.CommentDao;
import com.tyzz.blog.entity.Comment;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.CommentPageDTO;
import com.tyzz.blog.entity.dto.CommentDTO;
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


    public void comment(CommentDTO commentDTO, User user) {
        Comment comment = Comment.builder()
                .articleId(commentDTO.getArticleId())
                .replyId(commentDTO.getReplyId())
                .parentId(commentDTO.getParentId())
                .content(commentDTO.getContent())
                .userId(user.getUserId())
                .build();
        commentDao.insert(comment);
    }

    public Page<Comment> listPage(CommentPageDTO pageVO) {
        Page<Comment> page = Page.of(pageVO.getPage(), pageVO.getSize());
        return commentDao.selectPage(page, null);
    }
}