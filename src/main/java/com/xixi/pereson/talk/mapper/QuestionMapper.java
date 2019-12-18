package com.xixi.pereson.talk.mapper;

import com.xixi.pereson.talk.Model.Question;
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
    @Select("insert into question(title,description,gmt_create,gmt_modified,creator,tag)" +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insertQuestion(Question question);
    
    /**
    * @Description: 查询说有question表数据
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/17
    */
    @Select("select * from question")
    List<Question> selectQuestion();

}
