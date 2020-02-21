package com.xixi.person.talk.Service;

import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.Model.Question;

import java.io.IOException;
import java.util.List;

/**
 * @Author: xixi-98
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
    Question  insquestion(QuestionDto questionDto);
    
    /**
    * @Description: 为了获得user表的avatar_url数据 ，查询所有的QuestionDto数据,要使用业务装配的方式完成，
     * 先获得question集合，在增加user对象
    * @Param:
    * @return:
    * @Author: xixi
    * @Date: 2019/12/17
    */
    PageInfo selQuestionList(Long accountid,int size, int page,String search,String tag) throws IOException;


    
    /**
    * @Description:  根据问题id 查询出问题以及问题的用户 包装成QuestionDto对象
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/20
    */
    QuestionDto selQuestionByid(Long id);
    
    /**
    * @Description:  进行修改操作
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/21
    */
    Question updateQuestion(QuestionDto questionDto);
    
    /**
    * @Description: 添加问题浏览量
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/23
    */
    void insviewCount(Long id);
    
    /**
    * @Description: 查询出标签相同的问题
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/27
    */
    List<Question> selTagrealted(QuestionDto question);
}
