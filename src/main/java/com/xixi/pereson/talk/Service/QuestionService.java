package com.xixi.pereson.talk.Service;

import com.xixi.pereson.talk.Model.Question;
import com.xixi.pereson.talk.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/17 20:45
 * @Description:
 */

public interface QuestionService {
    
    /**
    * @Description:  向数据库插入question数据 
    * @Param: question
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/17
    */
    void  insquestion(Question question);
    
    /**
    * @Description: 为了获得user表的avatar_url数据 ，查询所有的QuestionDto数据,要使用业务装配的方式完成，
     * 先获得question集合，在增加user对象
    * @Param:
    * @return:
    * @Author: xixi
    * @Date: 2019/12/17
    */
    List<QuestionDto> selQuestionList();

}
