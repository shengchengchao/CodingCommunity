package com.xixi.person.talk.Service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.model.Question;
import com.xixi.person.talk.utils.Pageutils;

import java.util.List;

public interface QuestionService extends IService<Question> {


    Pageutils selQuestionList(Long id, int size, int page, String search, String tag, String sort);

    QuestionDto selQuestionByid(Long id);
    /**
     * @ Description :  插入数据
     * @ Author :  shengchengchao
     * @ Date :  2020/5/2
     */
    Question insquestion(QuestionDto questionDto);

    Question updateQuestion(QuestionDto questionDto);

    void insviewCount(Long id);

    List<Question> selTagrealted(QuestionDto question);
}
