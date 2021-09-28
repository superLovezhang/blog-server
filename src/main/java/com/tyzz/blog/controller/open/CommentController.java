package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.vo.CommentPageVO;
import com.tyzz.blog.entity.vo.CommentVO;
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

    @PutMapping
    public Result comment(@Validated @RequestBody CommentVO commentVO) {
        User user = userService.currentUser();
        commentService.comment(commentVO, user);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(CommentPageVO pageVO) {
        return Result.success();
    }
}