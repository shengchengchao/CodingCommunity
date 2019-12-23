package com.xixi.person.talk.Service;

import com.xixi.person.talk.model.User;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/16 19:33
 * @Description:
 */
public interface UserService {
    
    /**
    * @Description:  添加用户
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/23
    */
    void insUser(User users);
    
    /**
    * @Description:  根据token查询用户
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/23
    */
    User selUser(String token);

}
