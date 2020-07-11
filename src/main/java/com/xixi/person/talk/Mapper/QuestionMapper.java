package com.xixi.person.talk.mapper;


import com.xixi.person.talk.Config.SuperMapper;
import com.xixi.person.talk.model.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface QuestionMapper extends SuperMapper<Question> {

    /**
     * @Description: 让问题的评论数加1
     * @Author: shengchengchao
     * @Date: 2020/5/2
     * @param:
     * @return:
     **/
    void updatecommentConunt(Question question);

    void updatevicwConunt(Question question);

    List<Question> selQuestionTag(Question question);
}