package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.vo.UserVO;
import com.tyzz.blog.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/open/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public Result login(@RequestParam String email, @RequestParam String password) {
        return Result.success(userService.login(email, password));
    }

    @PutMapping("/register")
    public Result register(@RequestBody UserVO user) {
        userService.register(user);
        return Result.success();
    }
}