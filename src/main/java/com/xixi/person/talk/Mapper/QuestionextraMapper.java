package com.xixi.person.talk.Mapper;

import com.xixi.person.talk.Model.Question;

import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/23 21:46
 * @Description:
 */
public interface QuestionextraMapper {

    void updatevicwConunt(Question question);

    void updatecommentConunt(Question question);

    List<Question> selQuestionTag(Question question);
}
