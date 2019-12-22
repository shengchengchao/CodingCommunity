package com.xixi.person.talk.Service.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.dto.PaginationDto;
import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.mapper.QuestionMapper;
import com.xixi.person.talk.mapper.UserMapper;
import com.xixi.person.talk.model.Question;
import com.xixi.person.talk.model.QuestionExample;
import com.xixi.person.talk.model.User;
import com.xixi.person.talk.model.UserExample;
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
    public void insquestion(QuestionDto questionDto) {
        Question question=new Question();
        BeanUtils.copyProperties(questionDto,question);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreatorId(questionDto.getUser().getAccountId());
        questionMapper.insertSelective(question);
    }

    @Override
    public PageInfo selQuestionList(String accountId,int size, int page) {
        //查询出当前页 问题
        PageHelper.startPage(page,size);
        QuestionExample quesionExample = new QuestionExample();
        //判断accountId是否存在
        if(accountId!=null && ! accountId.equals("")){
            quesionExample.createCriteria().andCreatorIdEqualTo(accountId);
        }
        //问题总数
        List<Question> questions = questionMapper.selectByExampleWithBLOBs(quesionExample);
        //查询出当前页问题，navugatepage是导航页
        PageInfo pageInfo=new PageInfo(questions,5);
        List<QuestionDto> list = new ArrayList();
        List pageInfoList = pageInfo.getList();
        for (Object o : pageInfoList) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(o,questionDto);
            UserExample userExample = new UserExample();
            String accountid =((Question)o).getCreatorId();
            userExample.createCriteria().andAccountIdEqualTo(accountid);
            List<User> users = userMapper.selectByExample(userExample);
            questionDto.setUser(users.get(0));
            list.add(questionDto);
        }
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public QuestionDto selQuestionByid(int id) {
        QuestionDto questionDto=new QuestionDto();

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        List<Question> questions = questionMapper.selectByExampleWithBLOBs(questionExample);
        BeanUtils.copyProperties(questions.get(0),questionDto);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(questions.get(0).getCreatorId());
        List<User> users = userMapper.selectByExample(userExample);
        questionDto.setUser(users.get(0));
        return questionDto;
    }

    @Override
    public void updateQuestion(QuestionDto questionDto) {
        Question question=new Question();
        BeanUtils.copyProperties(questionDto,question);
        question.setGmtModified(System.currentTimeMillis());
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(question.getId());
        questionMapper.updateByExampleSelective(question, questionExample);
    }


}
