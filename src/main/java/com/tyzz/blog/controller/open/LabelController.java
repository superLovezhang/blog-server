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

    @PostMapping("/save")
    public Result save(@Validated LabelDTO labelDTO) {
        labelService.save(labelDTO);
        return Result.success();
    }

    @DeleteMapping("/remove/{labelId}")
    public Result remove(@PathVariable Long labelId) {
        labelService.remove(labelId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(LabelPageDTO labelPageDTO) {
        return Result.success(labelService.listPage(labelPageDTO));
    }

    @GetMapping("/hot")
    public Result hot() {
        return Result.success(labelService.hotList());
    }
}