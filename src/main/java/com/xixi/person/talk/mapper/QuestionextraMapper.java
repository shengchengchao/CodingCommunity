package com.xixi.person.talk.mapper;

import com.xixi.person.talk.model.Question;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/23 21:46
 * @Description:
 */
public interface QuestionextraMapper {

    void updatevicwConunt(Question question);

    void updatecommentConunt(Question question);
}
