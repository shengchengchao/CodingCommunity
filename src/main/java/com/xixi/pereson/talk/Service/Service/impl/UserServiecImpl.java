package com.xixi.pereson.talk.Service.Service.impl;

import com.xixi.pereson.talk.Model.Users;
import com.xixi.pereson.talk.Service.UserService;
import com.xixi.pereson.talk.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    
    /**
    * @Description:  将用户插入到数据库中，对于数据库中已有用户，需要更新他的数据（token updatetime 必须的，name可能要更改）
    * @Param: [user]
    * @return: void
    * @Author: xixi
    * @Date: 2019/12/18
    */
    public void insUser(Users user) {
        List<Users> users = userMapper.selectAllUser();
       
        if(users.size() != 0){
            for (Users u : users) {
                if(u.getAccountid().equals(user.getAccountid())){
                    user.setUpdatedate(System.currentTimeMillis());
                    userMapper.updateToken(user);
                    break;
                }else{
                    userMapper.insretUser(user);
                }
            }
        }else{
            userMapper.insretUser(user);
        }
    }
    @Override
    public Users selUser(String token) {
        return userMapper.selectUsers(token);
    }
}
