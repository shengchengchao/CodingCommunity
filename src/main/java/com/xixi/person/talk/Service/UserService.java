package com.xixi.person.talk.Service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xixi.person.talk.model.User;

public interface UserService extends IService<User> {


    User selUser(String token);

    void saveorupd(User user);
}
