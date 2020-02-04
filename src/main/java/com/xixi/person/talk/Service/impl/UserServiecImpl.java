package com.xixi.person.talk.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.mapper.UserMapper;
import com.xixi.person.talk.model.User;
import com.xixi.person.talk.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
    * @Description:  将用户插入到数据库中，对于数据库中已有用户，需要更新他的数据（token updatetime 必须的，name可能要更改）
    * @Param: [user]
    * @return: void
    * @Author: xixi
    * @Date: 2019/12/18
    */
    @Override
    @Transactional
    public void insUser(User user) {
        //查询所有的use用户
        List<User> users = userMapper.selectByExample(new UserExample());
        if(users.size() != 0){
            for (User u : users) {
                //id相同 表明是同一个用户 我们需要token与更新时间
                if(u.getAccountId().equals(user.getAccountId())){
                    user.setUpdateDate(System.currentTimeMillis());
                    UserExample example = new UserExample();
                    example.createCriteria().andIdEqualTo(u.getId());
                    userMapper.updateByExampleSelective(user,example);
                    break;
                }
            }
        }else{
            userMapper.insert(user);
        }
        //插入redis
        String useString= JSONObject.toJSONString(user);
        String tokens=String.valueOf(user.getToken());
        redisTemplate.opsForValue().set(tokens,useString);
    }
    @Override
    public User selUser(String token) {
        //先在redis中查询 如果失败 在mysql中查询 并将查询结果放入redis中
        String obj = (String)redisTemplate.opsForValue().get(token);
        User user = JSON.parseObject(obj, User.class);
        if(user!=null){
            return user;
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        if(users!=null){
            String useString= JSONObject.toJSONString(users.get(0));
            String tokens=String.valueOf(user.getToken());
            redisTemplate.opsForValue().set(tokens,useString);
            return users.get(0);
        }
        return null;

    }
}
