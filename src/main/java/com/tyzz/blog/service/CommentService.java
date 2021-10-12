package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.CommentDao;
import com.tyzz.blog.entity.Comment;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.CommentDTO;
import com.tyzz.blog.entity.dto.CommentPageDTO;
import com.tyzz.blog.entity.vo.CommentTreeVO;
import com.tyzz.blog.entity.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private UserService userService;


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

    public BlogPage<CommentTreeVO> listPage(CommentPageDTO pageDTO) {
        BlogPage<Comment> page = BlogPage.of(pageDTO.getPage(), pageDTO.getSize());
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parentId", null);
        if (pageDTO.getArticleId() != null) {
            wrapper.eq("articleId", pageDTO.getArticleId());
        }
        BlogPage<Comment> commentPage = commentDao.selectPage(page, wrapper);
        return commentPage.map(this::buildCommentTree);
    }

    private CommentTreeVO buildCommentTree(Comment comment) {
        CommentTreeVO commentTreeVO = new CommentTreeVO();
        BeanUtils.copyProperties(comment, commentTreeVO);
        User user = userService.selectById(comment.getUserId());
        commentTreeVO.setUser(userService.pojoToVO(user));
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parentId", comment.getCommentId());
        wrapper.orderByAsc("createTime");
        List<Comment> children = commentDao.selectList(wrapper);
        commentTreeVO.setChildren(assembledChildren(children));
        return commentTreeVO;
    }


    private List<CommentVO> assembledChildren(List<Comment> children) {
        return children.stream()
                .map(this::pojoToVO)
                .collect(Collectors.toList());
    }

    public CommentVO pojoToVOWithoutReply(Comment comment) {
        if (comment == null) {
            return null;
        }
        User user = userService.selectById(comment.getUserId());
        return CommentVO.builder()
                .createTime(comment.getCreateTime())
                .updateTime(comment.getUpdateTime())
                .like(comment.getLike())
                .content(comment.getContent())
                .user(userService.pojoToVO(user))
                .build();
    }

    public CommentVO pojoToVO(Comment comment) {
        CommentVO commentVO = pojoToVOWithoutReply(comment);
        Comment replyComment = commentDao.selectById(comment.getReplyId());
        commentVO.setReplyComment(pojoToVOWithoutReply(replyComment));
        return commentVO;
    }
}