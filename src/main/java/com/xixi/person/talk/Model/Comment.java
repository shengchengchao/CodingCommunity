package com.xixi.person.talk.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comment")
public class Comment  implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;
    @TableField("parent_id")
    private Long parentId;
    @TableField("type")
    private Integer type;
    @TableField("commentator")
    private Long commentator;
    @TableField("gmt_create")
    private Long gmtCreate;
    @TableField("gmt_modified")
    private Long gmtModified;
    @TableField("like_count")
    private Long likeCount;
    @TableField("content")
    private String content;
    @TableField("comment_count")
    private Integer commentCount;


}
