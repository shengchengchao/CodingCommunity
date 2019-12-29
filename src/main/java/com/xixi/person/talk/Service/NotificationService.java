package com.xixi.person.talk.Service;

import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.model.Notification;
import com.xixi.person.talk.model.User;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/29 17:52
 * @Description:
 */
public interface NotificationService {

    PageInfo selNotificationList(Long accountId, int size, int page);

    int unreadCount(Long id);

    Notification read(Long id, User user);
}
