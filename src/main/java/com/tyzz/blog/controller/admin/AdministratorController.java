package com.tyzz.blog.controller.admin;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.convert.AdministratorConverter;
import com.tyzz.blog.entity.dto.LoginDTO;
import com.tyzz.blog.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * (Administrator)表控制层
 *
 * @author makejava
 * @since 2022-01-17 10:55:29
 */
@RestController
@RequestMapping("/admin/administrator")
@RequiredArgsConstructor
@Validated
public class AdministratorController {
    private final AdministratorService administratorService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDTO login) {
        return Result.success(administratorService.login(login.getEmail(), login.getPassword()));
    }

    @GetMapping("/userInfo")
    public Result userInfo() {
        return Result.success(AdministratorConverter.INSTANCE.admin2AdminVO(administratorService.currentAdmin()));
    }
}