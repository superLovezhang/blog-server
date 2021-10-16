package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.dto.UserDTO;
import com.tyzz.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    /**
     * 登录
     * @param email
     * @param password
     * @return
     */
    @GetMapping("/login")
    public Result login(@RequestParam String email, @RequestParam String password) {
        return Result.success(userService.login(email, password));
    }

    @GetMapping("/userInfo")
    public Result userInfo() {
        return Result.success(userService.currentUser());
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PutMapping("/register")
    public Result register(@Validated @RequestBody UserDTO user) {
        userService.register(user);
        return Result.success();
    }
}