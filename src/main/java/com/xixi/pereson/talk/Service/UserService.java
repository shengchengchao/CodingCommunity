package com.xixi.pereson.talk.Service;

import com.xixi.pereson.talk.Model.Users;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/16 19:33
 * @Description:
 */
public interface UserService {

    void insUser(Users users);

    Users selUser(String token);
}
