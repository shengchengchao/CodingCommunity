package com.xixi.person.talk.Service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.xixi.person.talk.Service.UserService;

import com.xixi.person.talk.mapper.UserMapper;
import com.xixi.person.talk.model.User;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User selUser(String token) {
        //在mysql中查询
        User seluser = new LambdaQueryChainWrapper<User>(userMapper).eq(User::getToken, token).one();
        if(seluser!=null){
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
            user.setId(seluser.getId());
            userMapper.updateById(user);
        }


    }
}
