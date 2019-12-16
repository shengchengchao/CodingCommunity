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

    @Insert("insert into user (name,account_id,token,create_date,update_date)" +
            "values (#{name},#{accountid},#{toekn},#{createdate},#{updatedate})")
    void insretUser(Users user);

    @Select("select * from user where token=#{token}")
    Users selectUsers(@Param("token") String token);
}
