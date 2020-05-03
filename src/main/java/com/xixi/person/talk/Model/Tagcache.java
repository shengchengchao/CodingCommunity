package com.xixi.person.talk.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tagcache")
public class Tagcache  implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Integer id;
    @TableField("cache")
    private String cache;
    @TableField("name")
    private String name;

}
