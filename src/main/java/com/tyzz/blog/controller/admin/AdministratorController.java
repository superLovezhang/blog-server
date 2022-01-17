package com.tyzz.blog.controller.admin;

import com.tyzz.blog.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Administrator)表控制层
 *
 * @author makejava
 * @since 2022-01-17 10:55:29
 */
@RestController
@RequestMapping("/admin/administrator")
@RequiredArgsConstructor
public class AdministratorController {
    private final AdministratorService administratorService;

}