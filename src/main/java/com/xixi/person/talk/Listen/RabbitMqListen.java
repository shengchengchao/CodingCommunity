package com.xixi.person.talk.Listen;

import com.xixi.person.talk.mapper.NotificationMapper;
import com.xixi.person.talk.model.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Auther: xixi-98
 * @Date: 2020/2/10 20:45
 * @Description:
 */
@Component
public class RabbitMqListen {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private NotificationMapper notificationMapper;
    @RabbitListener(queues = "notification")
    public void process(Notification notification) {
        if(notification!=null){
            logger.error(notification.toString());
            notificationMapper.insert(notification);
        }
    }
}
