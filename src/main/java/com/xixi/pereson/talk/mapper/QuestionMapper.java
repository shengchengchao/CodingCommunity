package com.xixi.pereson.talk.mapper;

import com.xixi.pereson.talk.Model.Question;
import com.xixi.pereson.talk.dto.QuestionDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/17 20:15
 * @Description:
 */
public interface QuestionMapper {
    
    /**
    * @Description: 向数据库中question插入数据
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/17
    */
    @Select("insert into question(title,description,gmt_create,gmt_modified,creator_id,tag)" +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creatorid},#{tag})")
    void insertQuestion(Question question);
    

    
    /**
    * @Description: 分页显示
    * @Param: offset 偏移量  size 总条数
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/19
    */
    @Select("select * from question limit #{offset},#{size}")
    List<Question> selectQuestionBypage(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    
    /**
    * @Description: 查询所有总条数
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/19
    */
    @Select( "select count(1) from question")
    Integer count();
}
