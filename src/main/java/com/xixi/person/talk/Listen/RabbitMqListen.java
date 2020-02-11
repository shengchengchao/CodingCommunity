package com.xixi.person.talk.Listen;

import com.xixi.person.talk.Service.NotificationService;
import com.xixi.person.talk.mapper.NotificationMapper;
import com.xixi.person.talk.model.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Auther: xixi-98
 * @Date: 2020/2/10 20:45
 * @Description: 消息监听类 监听notification中消息。
 */
@Component
public class RabbitMqListen {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private NotificationService notificationServiceImpl;
    @Resource
    private NotificationMapper notificationMapper;
    @RabbitListener(queues = "notification")
    public void process(Notification notification) {
        if(notification!=null){
            logger.error(notification.toString());
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
