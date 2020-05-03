package com.xixi.person.talk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName= "question",type ="question")
public class QuestionInfo {

    @Field(type = FieldType.Keyword)
    private String id;
    /**
     * 修改时间
     */
    @Field(type = FieldType.Long)
    private Long gmtModified;
    /**
     * 问题题目
     */
    @Field(type = FieldType.Keyword)
    private String title;
    /**
     * 问题描述
     */
    @Field(type = FieldType.Long)
    private String description;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Long)
    private Long gmtCreate;
    /**
     * 创建人ID
     */
    @Field(type = FieldType.Long)
    private Long creatorId;
    /**
     * 标签
     */
    @Field(type = FieldType.Keyword)
    private String tag;
    /**
     * 评论数
     */
    @Field(type = FieldType.Integer)
    private Integer commentCount;
    /**
     * 喜欢数
     */
    @Field(type = FieldType.Integer)
    private  Integer likeCount;
    /**
     * 查看数
     */
    @Field(type = FieldType.Integer)
    private Integer viewCount;



}
