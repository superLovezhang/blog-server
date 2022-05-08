package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.enums.LikeType;
import com.tyzz.blog.service.ILike;
import com.tyzz.blog.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * 点赞文章、评论接口
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-07 17:07
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/open/like")
public class LikeController {
    private final UserService userService;
    private final ApplicationContext context;

    /**
     * 点赞或者取消点赞评论/文章
     * @return Result {@link Result}
     */
    @PostMapping("/{id}")
    public Result like(@PathVariable Long id, @RequestParam LikeType likeType) {
        User user = userService.currentUserNotExistThrowException();
        //策略模式 减少耦合
        context.getBean(likeType.getBeanName(), ILike.class).like(id, user);
        return Result.success();
    }
}

