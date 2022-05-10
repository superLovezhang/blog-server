package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.CommentDao;
import com.tyzz.blog.entity.convert.CommentConverter;
import com.tyzz.blog.entity.dto.CommentAdminPageDTO;
import com.tyzz.blog.entity.dto.CommentDTO;
import com.tyzz.blog.entity.dto.CommentPageDTO;
import com.tyzz.blog.entity.pojo.Comment;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.pojo.UserLike;
import com.tyzz.blog.entity.vo.CommentAdminVO;
import com.tyzz.blog.entity.vo.CommentTreeVO;
import com.tyzz.blog.entity.vo.CommentVO;
import com.tyzz.blog.enums.LikeType;
import com.tyzz.blog.enums.NotificationType;
import com.tyzz.blog.enums.NotifyBehavior;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.service.ILike;
import com.tyzz.blog.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tyzz.blog.constant.BlogConstant.COMMENT_LIKE;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RequiredArgsConstructor
@Service("commentService")
public class CommentService implements ILike {
    private final CommentDao commentDao;
    private final UserService userService;
    private final NotificationService notificationService;
    private final UserLikeService userLikeService;
    private final CommonService commonService;
    private final ArticleService articleService;
    private final RedisService redisService;


    /**
     * 发表评论 如果是文章下的评论同时更新评论数
     * @param commentDTO 评论DTO
     * @param user 发表用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void comment(CommentDTO commentDTO, User user) {
        createByDTO(commentDTO, user);
        if (commentDTO.getArticleId() != null) {
            //发表评论的同时 更新文章评论数
            articleService.incrCommentCount(commentDTO.getArticleId());
        }
    }

    /**
     * 根据commentDTO{@link CommentDTO} 创建并插入评论
     * @param commentDTO 参数对象
     * @param user 操作用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected void createByDTO(CommentDTO commentDTO, User user) {
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

    /**
     * 根据分页对象获取评论列表
     * @param pageDTO
     * @return 分页集合
     */
    @Transactional(propagation = Propagation.SUPPORTS)
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

    /**
     * 根据父评论构建它的评论树型结构
     * @param comment 父评论
     * @return {@link CommentTreeVO} 树型评论对象
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    protected CommentTreeVO buildCommentTree(Comment comment) {
        CommentTreeVO commentTreeVO = new CommentTreeVO();
        BeanUtils.copyProperties(comment, commentTreeVO);
        User user = userService.selectById(comment.getUserId());
        User currentUser = userService.currentUser();
        Long commentId = comment.getCommentId();
        String commentKey = StringUtils.generateRedisKey(COMMENT_LIKE, commentId);

        commentTreeVO.setLikes(userLikeService.count(commentId, commentKey, LikeType.COMMENT));
        commentTreeVO.setSelfLike(redisService.sHasKey(commentKey, currentUser.getUserId()));
        commentTreeVO.setPics(comment.getArrayPics());
        commentTreeVO.setUser(userService.pojoToVO(user));
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", commentId);
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
        if (StringUtils.isNotEmpty(comment.getPics())) {
            pics = comment.getPics().split(",");
        }
        User currentUser = userService.currentUser();
        UserLike userLike = userLikeService.findOneByCommentAndUser(comment, currentUser);
        return CommentVO.builder()
                .commentId(comment.getCommentId())
                .createTime(comment.getCreateTime())
                .updateTime(comment.getUpdateTime())
                .likes(userLikeService.countByComment(comment))
                .selfLike(userLike != null)
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

    /**
     * 删除评论
     * @param commentId 评论id
     * @param user 用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(Long commentId, User user) {
        deleteSingleComment(commentId, user);
    }

    /**
     * 删除单个评论 同时做一些其他操作 eg：更新文章评论数
     * @param commentId 评论id
     * @param user 用户id
     */
    public void deleteSingleComment(@NonNull Long commentId, @Nullable User user) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("comment_id", commentId);
        if (user != null) {
            wrapper.eq("user_id", user.getUserId());
        }
        Comment comment = Optional.ofNullable(commentDao.selectOne(wrapper))
                .orElseThrow(() -> new BlogException("当前文章不存在"));

        if (comment.getArticleId() != null) {
            articleService.decreCommentCount(comment.getArticleId());
        }
        commentDao.delete(wrapper);
    }

    /**
     * 管理员获取评论分页列表
     * @param dto 查询参数对象
     * @return 评论分页对象
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public BlogPage<Comment> adminList(CommentAdminPageDTO dto) {
        BlogPage<Comment> page = BlogPage.of(dto.getPage(), dto.getSize());
        return commentDao.adminList(dto, page);
    }

    @Transactional
    public void adminRemove(long commentId, String removeReason) {
        User user = Optional.ofNullable(findUserByCommentId(commentId))
                .orElseThrow(() -> new BlogException("该评论不存在"));
        notificationService.createDeny(NotificationType.COMMENT, removeReason, NotifyBehavior.DELETE, user);
        deleteSingleComment(commentId, null);
    }

    private User findUserByCommentId(long commentId) {
        Comment comment = Optional.ofNullable(commentDao.selectById(commentId))
                .orElseThrow(() -> new BlogException("该评论不存在"));
        Long userId = comment.getUserId();
        return userService.selectById(userId);
    }
    
    public CommentAdminVO pojo2AdminVO(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentAdminVO commentAdminVO = CommentConverter.INSTANCE.comment2AdminVO(comment);
        commentAdminVO.setUser(userService.pojoToVO(userService.selectById(comment.getUserId())));
        return commentAdminVO;
    }

    public Comment selectOneById(Long id) {
        return commentDao.selectById(id);
    }

    @Override
    public void like(Long id, User user) {
        commonService.commonSetOperation(id, user, LikeType.COMMENT.getType(), this::getUserIdsByCommentId);
    }

    public List<Long> getUserIdsByCommentId(Long id) {
        Comment comment = Optional.of(selectOneById(id))
                .orElseThrow(() -> new BlogException("该评论不存在"));
        return userLikeService.findAllUserIdsByComment(comment.getCommentId());
    }
}