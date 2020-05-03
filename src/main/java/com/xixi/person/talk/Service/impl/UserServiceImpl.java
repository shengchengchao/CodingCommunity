package com.xixi.person.talk.Service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.xixi.person.talk.Service.UserService;

import com.xixi.person.talk.mapper.UserMapper;
import com.xixi.person.talk.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private UserMapper userMapper;

    @Override
    public User selUser(String token) {
        //先在redis中查询 如果失败 在mysql中查询 并将查询结果放入redis中
        String obj = (String)redisTemplate.opsForValue().get(token);
        User user = JSON.parseObject(obj, User.class);
        if(user!=null){
            return user;
        }
        User seluser = new LambdaQueryChainWrapper<User>(userMapper).eq(User::getToken, token).one();
        if(seluser!=null){
            String useString= JSONObject.toJSONString(seluser);
            String tokens=String.valueOf(user.getToken());
            redisTemplate.opsForValue().set(tokens,useString);
            return seluser;
        }
        return null;
    }

    @Override
    public void saveorupd(User user) {
        User seluser = new LambdaQueryChainWrapper<User>(userMapper).eq(User::getAccountId,user.getAccountId()).one();
        if (seluser == null) {
            //插入数据
            userMapper.insert(user);
        } else {
            //更新

            seluser.setUpdateDate(System.currentTimeMillis());
            userMapper.updateById(seluser);
        }

        //插入redis
        String useString= JSONObject.toJSONString(user);
        String tokens=String.valueOf(user.getToken());
        redisTemplate.opsForValue().set(tokens,useString);
    }
}
