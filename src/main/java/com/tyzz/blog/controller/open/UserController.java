package com.tyzz.blog.controller.open;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.UserDTO;
import com.tyzz.blog.entity.dto.UserPasswordDTO;
import com.tyzz.blog.entity.group.CreateGroup;
import com.tyzz.blog.entity.group.UpdateGroup;
import com.tyzz.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/open/user")
@Validated
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

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public Result userInfo() {
        return Result.success(userService.pojoToVO(userService.currentUser()));
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PutMapping("/register")
    public Result register(@Validated(CreateGroup.class) @RequestBody UserDTO user) {
        userService.register(user);
        return Result.success();
    }

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @return
     */
    @PutMapping("/verifyCode")
    @Validated
    public Result verifyCode(
            @RequestParam
            @Email(message = "邮箱格式不正确")
            String email
    ) {
        userService.sendRegisterVerificationCode(email);
        return Result.success();
    }

    /**
     * 保存用户信息
     * @return
     */
    @PostMapping("/save")
    public Result save(@Validated(UpdateGroup.class) @RequestBody UserDTO userDTO) {
        User user = userService.currentUserNotExistThrowException();
        userService.saveByDTO(user, userDTO);
        return Result.success();
    }

    /**
     * 修改用户密码
     * @return
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UserPasswordDTO userDTO) {
        User user = userService.currentUserNotExistThrowException();
        userService.updatePassword(userDTO, user);
        return Result.success();
    }
}