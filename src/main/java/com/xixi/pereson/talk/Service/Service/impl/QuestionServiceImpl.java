package com.xixi.pereson.talk.Service.Service.impl;

import com.xixi.pereson.talk.Model.Question;
import com.xixi.pereson.talk.Model.Users;
import com.xixi.pereson.talk.Service.QuestionService;
import com.xixi.pereson.talk.dto.PaginationDto;
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
    public PaginationDto selQuestionList(int size, int page) {
        //问题总数
        Integer totalCount = questionMapper.count();
        int totalPage=0;
        //确认总页数与当前页数
        if(totalCount % size == 0){
            totalPage=totalCount / size;
        }else{
            totalPage=(totalCount / size)+1;
        }
        if (page<1){
            page=1;
        }else if(page>totalPage){
            page=totalPage;
        }

        PaginationDto paginationDto=new PaginationDto();
        //设置需要显示的页码与
        paginationDto.setpagination(size,page,totalPage);

        //查询出当前页 问题
        List<Question> questions = questionMapper.selectQuestionBypage((page - 1) * size, size);

        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question q:questions) {
            //根据question的creatorid查询出 对应的用户信息
           Users user = userMapper.selectUsersByaccountid(q.getCreatorid());

            QuestionDto questionDto=new QuestionDto();
            //快速的将question中的数据 添加到questionDto对象中，使用get/set对象太复杂
            BeanUtils.copyProperties(q,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestionList(questionDtoList);


        return paginationDto;
    }

    @Override
    public PaginationDto selQuestionListByid(String accountid,int size, int page) {
        //问题总数
        Integer totalCount = questionMapper.countByaccountid(accountid);
        int totalPage=0;
        //确认总页数与当前页数
        if(totalCount % size == 0){
            totalPage=totalCount / size;
        }else{
            totalPage=(totalCount / size) +1;
        }
        if (page<1){
            page=1;
        }else if(page>totalPage){
            page=totalPage;
        }

        PaginationDto paginationDto=new PaginationDto();
        //设置需要显示的页码与
        paginationDto.setpagination(size,page,totalPage);

        //查询出当前页 问题
        List<Question> questions = questionMapper.selectQuestionByCreatorid(accountid,(page - 1) * size, size);

        List<QuestionDto> questionDtoList=new ArrayList<>();
        for (Question q:questions) {
            //根据question的creatorid查询出 对应的用户信息
            Users user = userMapper.selectUsersByaccountid(q.getCreatorid());

            QuestionDto questionDto=new QuestionDto();
            //快速的将question中的数据 添加到questionDto对象中，使用get/set对象太复杂
            BeanUtils.copyProperties(q,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestionList(questionDtoList);


        return paginationDto;
    }
}
