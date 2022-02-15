package com.tyzz.blog.controller.admin;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-02-14 17:06
 */

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.convert.CommentConverter;
import com.tyzz.blog.entity.dto.CommentAdminPageDTO;
import com.tyzz.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("adminCommentController")
@RequestMapping("/admin/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/list")
    public Result list(CommentAdminPageDTO dto) {
        return Result.success(commentService.adminList(dto).map(CommentConverter.INSTANCE::comment2AdminVO));
    }

    @DeleteMapping
    public Result remove(@RequestParam long commentId, @RequestParam String removeReason) {
        commentService.adminRemove(commentId, removeReason);
        return Result.success();
    }
}
