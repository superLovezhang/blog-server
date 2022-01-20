package com.tyzz.blog.controller.admin;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.entity.dto.UserAdminPageDTO;
import com.tyzz.blog.enums.UserStatus;
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

    /**
     * 管理端获取用户列表分页接口
     * @param dto 查询参数
     * @return 分页信息
     */
    @GetMapping("/list")
    public Result list(UserAdminPageDTO dto) {
        return Result.success(userService.listPage(dto).map(userService::pojoToVO));
    }

    /**
     * 禁用/恢复用户账号
     * @param userId 用户id
     * @param status 用户状态
     * @param frozenReason 禁用原因
     * @return 操作结果
     */
    @PostMapping("/ban")
    public Result ban(@RequestParam long userId,
                      @RequestParam(required = false, defaultValue = "NORMAL") UserStatus status,
                      String frozenReason
                      )
    {
        userService.ban(userId, status, frozenReason);
        return Result.success();
    }
}
