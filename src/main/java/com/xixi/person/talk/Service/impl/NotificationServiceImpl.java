package com.xixi.person.talk.Service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.xixi.person.talk.Service.NotificationService;
import com.xixi.person.talk.dto.NotificationDto;
import com.xixi.person.talk.enums.NotificationEnum;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.exception.QuestionException;
import com.xixi.person.talk.mapper.NotificationMapper;
import com.xixi.person.talk.mapper.QuestionMapper;
import com.xixi.person.talk.model.Notification;
import com.xixi.person.talk.model.Question;
import com.xixi.person.talk.model.User;
import com.xixi.person.talk.utils.Pageutils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    @Resource
    private NotificationMapper notificationMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private QuestionMapper questionMapper;
    /**
     * @Description: 查看通知
     * @Author: shengchengchao
     * @Date: 2020/5/2 
     * @param: 
     * @return: 
     **/
    @Override
    public Notification read(Long id, User user) {
        Notification notification = new LambdaQueryChainWrapper<Notification>(notificationMapper).
                eq(Notification::getId, id).one();
        if(notification==null){
            throw  new QuestionException(QuestionErrorCodeEnum.READ_NOTIFICATION_FAIL);
        }
        if (!notification.getReceiver().equals(user.getAccountId())){
            throw  new QuestionException(QuestionErrorCodeEnum.NOTIFICATION_NOT_FOUND);
        }
        notification.setStatus(NotificationEnum.READ.getType());
        boolean update = lambdaUpdate().update(notification);
        System.out.println(update);
        Integer unreadCount = (Integer) redisTemplate.opsForValue().get("unreadCount");
        if (unreadCount == null){
            unreadCount = this.unreadCount(notification.getReceiver());
            redisTemplate.opsForValue().set("unreadCount",unreadCount);
        }
        redisTemplate.opsForValue().set("unreadCount",unreadCount-1);
        return notification;
    }

    @Override
    public Pageutils<NotificationDto> selNotificationList(Long accountId, int size, int page) {
        Page<Notification> quePage =new Page<>(page,size);
        QueryWrapper<Notification> qw=new QueryWrapper<>();
        qw.orderByDesc("gmt_create");
        if(accountId != 0L){
            qw.eq("receiver",accountId);
        }
        IPage<Notification> notificationIPage = notificationMapper.selectPage(quePage, qw);
        notificationIPage.setSize(5L);
        List<NotificationDto> list = new ArrayList<>();
        List<Notification> records = notificationIPage.getRecords();
        records.forEach(each->{
            NotificationDto notificationDto =new NotificationDto();
            BeanUtils.copyProperties(each,notificationDto);
            notificationDto.setTypeName(NotificationEnum.getName(each.getType()));
            list.add(notificationDto);
        });
        Pageutils<NotificationDto> pages =new Pageutils<>(list,5);
        return pages;

    }
    /**
     * @Description: 查看未读数
     * @Author: shengchengchao
     * @Date: 2020/5/2
     * @param:
     * @return:
     **/
    @Override
    public Integer unreadCount(Long id) {
        List<Notification> list = new LambdaQueryChainWrapper<Notification>(notificationMapper).eq(Notification::getNotifier, id)
                .eq(Notification::getType, NotificationEnum.UEREAD.getType()).list();
        return list.size();
    }
}
