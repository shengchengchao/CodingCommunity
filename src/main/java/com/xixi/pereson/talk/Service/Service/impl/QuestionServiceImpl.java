package com.xixi.pereson.talk.Service.Service.impl;

import com.xixi.pereson.talk.Model.Question;
import com.xixi.pereson.talk.Model.Users;
import com.xixi.pereson.talk.Service.QuestionService;
import com.xixi.pereson.talk.dto.QuestionDto;
import com.xixi.pereson.talk.mapper.QuestionMapper;
import com.xixi.pereson.talk.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/17 20:46
 * @Description:
 */
@Service
public class QuestionServiceImpl implements QuestionService {
   @Resource
   private QuestionMapper questionMapper;
   @Resource
   private UserMapper userMapper;
    @Override
    public void insquestion(Question question) {
        questionMapper.insertQuestion(question);
    }

    @Override
    public List<QuestionDto> selQuestionList() {
        List<Question> questions = questionMapper.selectQuestion();
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question q:questions) {

            Users user = userMapper.selectUsersByname(q.getCreator());
            QuestionDto questionDto=new QuestionDto();
            //快速的将question中的数据 添加到questionDto对象中，使用get/set对象太复杂
            BeanUtils.copyProperties(q,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
