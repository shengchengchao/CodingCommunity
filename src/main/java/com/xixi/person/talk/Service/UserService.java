package com.xixi.person.talk.Service;

import com.xixi.person.talk.model.User;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/16 19:33
 * @Description:
 */
public interface UserService {

    void insUser(User users);

    User selUser(String token);
}
