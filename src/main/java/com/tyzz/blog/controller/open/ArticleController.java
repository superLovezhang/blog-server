package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.dto.ArticleDTO;
import com.tyzz.blog.entity.dto.ArticlePageDTO;
import com.tyzz.blog.service.impl.ArticleService;
import com.tyzz.blog.service.impl.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.stream.Collectors;

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
     *
     * @param articlePageDTO
     * @return
     */
    @GetMapping("/list")
    public Result list(ArticlePageDTO articlePageDTO) {
        if (articlePageDTO.getKey() != null) {
            User user = userService.currentUserNotExistThrowException();
            articlePageDTO.setUserId(user.getUserId());
        }
        return Result.success(articleService.listPage(articlePageDTO).map(articleService::pojo2ListVO));
    }

    /**
     * 保存文章
     *
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
     *
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
        return Result.success(articleService.hotList()
                .stream()
                .map(articleService::pojo2RecordVO)
                .collect(Collectors.toList()));
    }

    /**
     * 确保有权限的获取文章详情 用于编辑文章
     * @param articleId 文章ID
     * @return
     */
    @GetMapping("/ensurePermissionDetail")
    public Result ensurePermissionDetail(@RequestParam Long articleId) {
        User user = userService.currentUserNotExistThrowException();
        return Result.success(articleService.pojoToVO(articleService.selectOneByIdAndUser(articleId, user)));
    }

    /**
     * 删除文章
     * @param articleId 待删除文章id
     * @return
     */
    @DeleteMapping("/remove/{articleId}")
    public Result remove(@PathVariable long articleId) {
        User user = userService.currentUserNotExistThrowException();
        articleService.remove(articleId, user);
        return Result.success();
    }
}