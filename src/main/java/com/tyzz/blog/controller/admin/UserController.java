package com.tyzz.blog.controller.admin;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.dto.UserAdminPageDTO;
import com.tyzz.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-14 18:33
 */
@RequestMapping("/admin/user")
@RestController("adminUserController")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public Result list(UserAdminPageDTO dto) {
        return Result.success(userService.listPage(dto).map(userService::pojoToVO));
    }
}
