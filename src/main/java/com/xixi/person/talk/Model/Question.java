package com.xixi.person.talk.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("question")
public class Question  implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;
    @TableField("title")
    private String title;
    @TableField("description")
    private String description;
    @TableField("gmt_create")
    private Long gmtCreate;
    @TableField("gmt_modified")
    private Long gmtModified;
    @TableField("creator_id")
    private Long creatorId;
    @TableField("comment_count")
    private Integer commentCount;
    @TableField("view_count")
    private Integer viewCount;
    @TableField("like_count")
    private Integer likeCount;
    @TableField("tag")
    private String tag;

}
