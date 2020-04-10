package com.xixi.person.talk.Service;

import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.Model.Notification;
import com.xixi.person.talk.Model.User;

/**
 * @Author: xixi-98
 * @Date: 2019/12/29 17:52
 * @Description:
 */
public interface NotificationService {

    PageInfo selNotificationList(Long accountId, int size, int page);

    int unreadCount(Long id);

    Notification read(Long id, User user);
}
