package com.xixi.person.talk.Service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xixi.person.talk.dto.NotificationDto;
import com.xixi.person.talk.model.Notification;
import com.xixi.person.talk.model.User;
import com.xixi.person.talk.utils.Pageutils;

public interface NotificationService extends IService<Notification> {


    Notification read(Long id, User user);

    Pageutils<NotificationDto> selNotificationList(Long accountId, int size, int page);

    Integer unreadCount(Long receiver);
}
