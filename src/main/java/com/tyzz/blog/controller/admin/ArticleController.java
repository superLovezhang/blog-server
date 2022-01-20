package com.tyzz.blog.controller.admin;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.dto.ArticleAdminPageDTO;
import com.tyzz.blog.enums.ArticleStatus;
import com.tyzz.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-20 16:17
 */
@RequiredArgsConstructor
@RestController("adminArticleController")
@RequestMapping("/admin/article")
public class ArticleController {
    private final ArticleService articleService;

    /**
     * 管理端获取文章列表分页接口
     * @param dto 参数
     * @return 分页信息
     */
    @GetMapping("/list")
    public Result list(ArticleAdminPageDTO dto) {
        return Result.success(articleService.adminList(dto).map(articleService::pojoToVO));
    }

    /**
     * 审核文章接口
     * @param articleId 文章id
     * @param status 文章状态
     * @param refuseReason 拒绝原因
     * @return 操作信息
     */
    @PostMapping("/audit")
    public Result audit(
            @RequestParam Long articleId,
            @RequestParam ArticleStatus status,
            String refuseReason
    ) {
        articleService.audit(articleId, status, refuseReason);
        return Result.success();
    }
}
