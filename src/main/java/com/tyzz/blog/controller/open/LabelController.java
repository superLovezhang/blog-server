package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.dto.LabelDTO;
import com.tyzz.blog.entity.dto.LabelPageDTO;
import com.tyzz.blog.service.LabelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Label)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequestMapping("/open/label")
public class LabelController {
    @Resource
    private LabelService labelService;

    /**
     * 保存标签
     * @param labelDTO
     * @return
     */
    @PostMapping("/save")
    public Result save(@Validated LabelDTO labelDTO) {
        labelService.save(labelDTO);
        return Result.success();
    }

    /**
     * 移除标签
     * @param labelId
     * @return
     */
    @DeleteMapping("/remove/{labelId}")
    public Result remove(@PathVariable Long labelId) {
        labelService.remove(labelId);
        return Result.success();
    }

    /**
     * 获取分页标签列表
     * @param labelPageDTO
     * @return
     */
    @GetMapping("/list")
    public Result list(LabelPageDTO labelPageDTO) {
        return Result.success(labelService.listPage(labelPageDTO));
    }

    /**
     * 获取热门前十标签
     * @return
     */
    @GetMapping("/hot")
    public Result hot() {
        return Result.success(labelService.hotList());
    }

    @GetMapping("/listAll")
    public Result listAll() {
        return Result.success(labelService.listAll());
    }
}