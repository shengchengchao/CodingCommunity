package com.xixi.pereson.talk.mapper;

import com.xixi.pereson.talk.Model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/16 16:58
 * @Description:
 */
public interface UserMapper {
    
    /**
    * @Description: 向数据库中插入user数据
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/17
    */
    @Insert("insert into user (name,account_id,token,create_date,update_date,bio,avatar_url)" +
            "values (#{name},#{accountid},#{toekn},#{createdate},#{updatedate},#{bio},#{avatarUrl})")
    void insretUser(Users user);
    
    /**
    * @Description: 查询数据库user表 
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/17
    */
    @Select("select * from user where token=#{token}")
    Users selectUsers(@Param("token") String token);
    
    /**
    * @Description: 查询数据库user表
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/17
    */
    @Select("select * from user where name=#{name}")
    Users selectUsersByname(@Param("name") String name);
}
