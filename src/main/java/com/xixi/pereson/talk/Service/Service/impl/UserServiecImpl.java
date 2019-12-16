package com.xixi.pereson.talk.Service.Service.impl;

import com.xixi.pereson.talk.Model.Users;
import com.xixi.pereson.talk.Service.UserService;
import com.xixi.pereson.talk.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/16 19:35
 * @Description:
 */
@Service
public class UserServiecImpl implements UserService {
   @Resource
   private UserMapper userMapper;
    @Override
    public void insUser(Users users) {
        userMapper.insretUser(users);
    }

    @Override
    public Users selUser(String token) {
        return userMapper.selectUsers(token);
    }
}
