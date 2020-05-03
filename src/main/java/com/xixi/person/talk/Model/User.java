package com.xixi.person.talk.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User  implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;
    @TableField("account_id")
    private Long accountId;
    @TableField("name")
    private String name;
    @TableField("token")
    private String token;
    @TableField("create_date")
    private Long createDate;
    @TableField("update_date")
    private Long updateDate;
    @TableField("bio")
    private String bio;
    @TableField("avatar_url")
    private String avatarUrl;

}
