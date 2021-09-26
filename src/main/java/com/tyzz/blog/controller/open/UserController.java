package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/open/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public Result login(@RequestParam String email, @RequestParam String password) {
        userService.login(email, password);
        return Result.success();
    }

    @PutMapping("/register")
    public Result register() {
        return Result.success();
    }
}