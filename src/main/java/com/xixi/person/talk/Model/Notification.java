package com.xixi.person.talk.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("notification")
public class Notification  implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;
    @TableField("notifier")
    private Long notifier;
    @TableField("receiver")
    private Long receiver;
    @TableField("outerid")
    private Long outerid;
    @TableField("type")
    private Integer type;
    @TableField("gmt_create")
    private Long gmtCreate;
    @TableField("status")
    private Integer status;
    @TableField("notifier_name")
    private String notifierName;
    @TableField("outer_title")
    private String outerTitle;

}
