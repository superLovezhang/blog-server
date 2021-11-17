package com.tyzz.blog.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyzz.blog.entity.group.CreateGroup;
import com.tyzz.blog.entity.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 13:37
 */
@Data
public class UserDTO {
    @Email(message = "邮箱格式错误", groups = {CreateGroup.class, UpdateGroup.class})
    private String email;

    @NotBlank(message = "昵称不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    private String username;

    @NotBlank(message = "密码不能为空", groups = CreateGroup.class)
    private String password;

    @NotBlank(message = "验证码不能为空", groups = CreateGroup.class)
    private String verifyCode;

    private Integer gender;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date birthday;

    private String description;

    private String city;

    private String avatar;
}
