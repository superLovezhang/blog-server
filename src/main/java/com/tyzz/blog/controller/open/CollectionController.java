package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.dto.BasePageDTO;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.service.impl.CollectionService;
import com.tyzz.blog.service.impl.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Collection)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequestMapping("/open/collection")
public class CollectionController {
    @Resource
    private CollectionService collectionService;
    @Resource
    private UserService userService;

    /**
     * 获取收藏文章列表
     * @param pageVO 分页参数
     * @return
     */
    @GetMapping("/list")
    public Result list(BasePageDTO pageVO) {
        User user = userService.currentUserNotExistThrowException();
        return Result.success(collectionService.pageByUser(user, pageVO));
    }

    /**
     * 获取最近个人收藏文章列表
     * @param size 列表长度 默认5
     * @return
     */
    @GetMapping("/recent")
    public Result recent(@RequestParam(required = false, defaultValue = "5") int size) {
        User user = userService.currentUserNotExistThrowException();
        return Result.success(collectionService.recentRecord(user, size));
    }

    /**
     * 收藏/取消收藏文章
     * @param articleId 文章ID
     * @return
     */
    @PostMapping("/collect/{articleId}")
    public Result collect(@PathVariable Long articleId) {
        User user = userService.currentUserNotExistThrowException();
        collectionService.collect(user, articleId);
        return Result.success();
    }
}