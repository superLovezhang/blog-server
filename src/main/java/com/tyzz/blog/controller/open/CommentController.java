package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.CommentPageDTO;
import com.tyzz.blog.entity.dto.CommentDTO;
import com.tyzz.blog.service.CommentService;
import com.tyzz.blog.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Comment)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequestMapping("/open/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    /**
     * 发表评论
     * @param commentDTO
     * @return
     */
    @PutMapping
    public Result comment(@Validated @RequestBody CommentDTO commentDTO) {
        User user = userService.currentUserNotExistThrowException();
        commentService.comment(commentDTO, user);
        return Result.success();
    }

    /**
     * 获取评论列表
     * @param pageDTO
     * @return
     */
    @GetMapping("/list")
    public Result list(CommentPageDTO pageDTO) {
        return Result.success(commentService.listPage(pageDTO));
    }

    /**
     * 点赞或者取消点赞评论
     * @return
     */
    @PostMapping("/like/{commentId}")
    public Result like(@PathVariable Long commentId) {
        User user = userService.currentUserNotExistThrowException();
        commentService.like(commentId, user);
        return Result.success();
    }

    /**
     * 删除评论
     * @param commentId 评论id
     * @return
     */
    @DeleteMapping("/remove/{commentId}")
    public Result remove(@PathVariable Long commentId) {
        User user = userService.currentUserNotExistThrowException();
        commentService.remove(commentId, user);
        return Result.success();
    }
}