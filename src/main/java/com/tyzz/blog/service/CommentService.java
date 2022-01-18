package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.CommentDao;
import com.tyzz.blog.entity.pojo.Comment;
import com.tyzz.blog.entity.pojo.CommentUser;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.dto.CommentDTO;
import com.tyzz.blog.entity.dto.CommentPageDTO;
import com.tyzz.blog.entity.vo.CommentTreeVO;
import com.tyzz.blog.entity.vo.CommentVO;
import com.tyzz.blog.exception.BlogException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
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
    @Resource
    private CommentUserService commentUserService;


    public void comment(CommentDTO commentDTO, User user) {
        Comment comment = Comment.builder()
                .articleId(commentDTO.getArticleId())
                .replyId(commentDTO.getReplyId())
                .parentId(commentDTO.getParentId())
                .content(commentDTO.getContent())
                .pics(commentDTO.getPics())
                .userId(user.getUserId())
                .build();
        commentDao.insert(comment);
    }

    public BlogPage<CommentTreeVO> listPage(CommentPageDTO pageDTO) {
        BlogPage<Comment> page = BlogPage.of(pageDTO.getPage(), pageDTO.getSize());
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.isNull("parent_id");
        if (pageDTO.getArticleId() != null) {
            wrapper.eq("article_id", pageDTO.getArticleId());
        } else {
            wrapper.isNull("article_id");
        }
        wrapper.orderByDesc(pageDTO.getUnderscoreSortColumn());
        BlogPage<Comment> commentPage = commentDao.selectPage(page, wrapper);
        return commentPage.map(this::buildCommentTree);
    }

    private CommentTreeVO buildCommentTree(Comment comment) {
        CommentTreeVO commentTreeVO = new CommentTreeVO();
        BeanUtils.copyProperties(comment, commentTreeVO);
        User user = userService.selectById(comment.getUserId());
        User currentUser = userService.currentUser();
        CommentUser commentUser = commentUserService.findOneByCommentAndUser(comment, currentUser);

        commentTreeVO.setLikes(commentUserService.countByComment(comment));
        commentTreeVO.setSelfLike(commentUser != null);
        commentTreeVO.setPics(comment.getArrayPics());
        commentTreeVO.setUser(userService.pojoToVO(user));
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", comment.getCommentId());
        wrapper.orderByAsc("create_time");
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
        String[] pics = null;
        if (StringUtils.isNotBlank(comment.getPics())) {
            pics = comment.getPics().split(",");
        }
        User currentUser = userService.currentUser();
        CommentUser commentUser = commentUserService.findOneByCommentAndUser(comment, currentUser);
        return CommentVO.builder()
                .commentId(comment.getCommentId())
                .createTime(comment.getCreateTime())
                .updateTime(comment.getUpdateTime())
                .likes(commentUserService.countByComment(comment))
                .selfLike(commentUser != null)
                .content(comment.getContent())
                .pics(pics)
                .user(userService.pojoToVO(user))
                .articleId(comment.getArticleId())
                .build();
    }

    public CommentVO pojoToVO(Comment comment) {
        CommentVO commentVO = pojoToVOWithoutReply(comment);
        Comment replyComment = commentDao.selectById(comment.getReplyId());
        commentVO.setReplyComment(pojoToVOWithoutReply(replyComment));
        return commentVO;
    }

    public Integer countByArticleId(Long articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        return commentDao.selectCount(wrapper);
    }

    public void like(Long commentId, User user) {
        Comment comment = Optional.of(commentDao.selectById(commentId))
                .orElseThrow(() -> new BlogException("该评论不存在"));
        CommentUser commentUser = commentUserService.findOneByCommentAndUser(comment, user);
        if (commentUser == null) {
            commentUserService.create(comment, user);
            return;
        }
        commentUserService.remove(commentUser);
    }

    public void remove(Long commentId, User user) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", commentId);
        wrapper.eq("user_id", user.getUserId());
        commentDao.delete(wrapper);
    }
}