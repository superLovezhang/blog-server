package com.tyzz.blog.service.impl;

import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.dao.NotificationDao;
import com.tyzz.blog.entity.pojo.Notification;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.enums.NotificationType;
import com.tyzz.blog.enums.NotifyBehavior;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    public Notification createSuccess(NotificationType type, User user) {
        return createNotification(type, user, String.format(BlogConstant.NOTIFICATION_SUCCESS_TEMPLATE, type.getType()));
    }

    private Notification createNotification(NotificationType type, User user, String content) {
        return Notification.builder()
                .content(content)
                .notificationType(type)
                .userId(user.getUserId())
                .build();
    }

    public Notification createDeny(
            NotificationType type,
            String reason,
            NotifyBehavior behavior,
            User user
    ) {
        Notification notification = createNotification(
                type,
                user,
                String.format(BlogConstant.NOTIFICATION_DENY_TEMPLATE, type.getType(), behavior.getDesc(), reason)
        );
        return notification;
    }

    /**
     * 保存通知
     * @param notification
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Notification notification) {
        notificationDao.insert(notification);
    }
}