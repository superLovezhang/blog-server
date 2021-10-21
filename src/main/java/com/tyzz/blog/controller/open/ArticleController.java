package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.ArticleDTO;
import com.tyzz.blog.entity.dto.ArticlePageDTO;
import com.tyzz.blog.service.ArticleService;
import com.tyzz.blog.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequestMapping("/open/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private UserService userService;

    /**
     * 获取文章列表
     * @param articlePageDTO
     * @return
     */
    @GetMapping("/list")
    public Result list(ArticlePageDTO articlePageDTO) {
        return Result.success(articleService.listPage(articlePageDTO).map(articleService::pojoToVO));
    }

    /**
     * 保存文章
     * @param articleDTO
     * @return
     */
    @PostMapping("/save")
    public Result save(@Validated @RequestBody ArticleDTO articleDTO) {
        User user = userService.currentUserNotExistThrowException();
        articleService.save(user, articleDTO);
        return Result.success();
    }

    /**
     * 获取文章详情
     * @param articleId
     * @return
     */
    @GetMapping("/{articleId}")
    public Result detail(@PathVariable Long articleId) {
        return Result.success(articleService.pojoToVO(articleService.viewArticleDetail(articleId)));
    }

    /**
     * 获取热门推荐文章
     * @return
     */
    @GetMapping("/hot")
    public Result hot() {
        return Result.success(articleService.hotList());
    }
}