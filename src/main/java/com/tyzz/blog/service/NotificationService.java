package com.tyzz.blog.service;

import com.tyzz.blog.dao.NotificationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * (Notification)表服务实现类
 *
 * @author makejava
 * @since 2022-02-15 15:32:24
 */
@Service("notificationService")
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationDao notificationDao;
}