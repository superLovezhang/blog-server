package com.tyzz.blog.controller.open;

import com.tyzz.blog.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Notification)表控制层
 *
 * @author makejava
 * @since 2022-02-15 15:32:24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/open/notification")
public class NotificationController {
    private final NotificationService notificationService;
}