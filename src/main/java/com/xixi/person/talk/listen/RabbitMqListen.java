package com.xixi.person.talk.listen;

import com.xixi.person.talk.Service.NotificationService;
import com.xixi.person.talk.Mapper.NotificationMapper;
import com.xixi.person.talk.Model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Author: xixi-98
 * @Date: 2020/2/10 20:45
 * @Description: 消息监听类 监听notification中消息。
 */
@Slf4j
@Component
public class RabbitMqListen {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private NotificationService notificationServiceImpl;
    @Resource
    private NotificationMapper notificationMapper;
    @RabbitListener(queues = "notification")
    public void process(Notification notification) {
        if(notification!=null){
            log.error(notification.toString());
            notificationMapper.insert(notification);
            Integer unreadCount = (Integer) redisTemplate.opsForValue().get("unreadCount");
            if (unreadCount == null){
                unreadCount = notificationServiceImpl.unreadCount(notification.getReceiver());
                redisTemplate.opsForValue().set("unreadCount",unreadCount);
            }
            redisTemplate.opsForValue().set("unreadCount",unreadCount+1);
        }

    }
}
