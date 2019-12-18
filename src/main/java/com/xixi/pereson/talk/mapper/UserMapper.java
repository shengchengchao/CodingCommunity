package com.xixi.pereson.talk.mapper;

import com.xixi.pereson.talk.Model.Users;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
    List<Users> selectUsersByname(@Param("name") String name);
    @Select("select * from user where account_id=#{accountid}")
    Users selectUsersByaccountid(String accountid);
    @Select("select * from user")
    List<Users> selectAllUser();

    @Update("update  user set name=#{name},token=#{token},update_date=#{updatedate} where account_id=#{accountid}")
    void updateToken(Users user);
}
