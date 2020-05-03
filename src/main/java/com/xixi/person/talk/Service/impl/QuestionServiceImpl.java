package com.xixi.person.talk.Service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.exception.QuestionException;
import com.xixi.person.talk.mapper.QuestionMapper;
import com.xixi.person.talk.mapper.UserMapper;
import com.xixi.person.talk.model.Question;
import com.xixi.person.talk.model.User;
import com.xixi.person.talk.utils.Pageutils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;


@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;


    @Override
    public Pageutils selQuestionList(Long id, int size, int page, String search, String tag, String sort) {
        return null;
    }

    @Override
    public QuestionDto selQuestionByid(Long id) {
        Question question = questionMapper.selectById(id);
        QuestionDto qd=new QuestionDto();
        BeanUtils.copyProperties(question,qd);
        User user = new LambdaQueryChainWrapper<User>(userMapper)
                .eq(User::getAccountId, question.getCreatorId()).one();
        qd.setUser(user);
        return qd;
    }

    @Override
    public Question insquestion(QuestionDto questionDto) {
        Question q = new Question();
        BeanUtils.copyProperties(questionDto,q);
        q.setGmtCreate(System.currentTimeMillis());
        q.setGmtModified(System.currentTimeMillis());
        q.setCreatorId(questionDto.getUser().getAccountId());
        questionMapper.insert(q);
        return q;
    }

    @Override
    public Question updateQuestion(QuestionDto questionDto) {
        Question q = new Question();
        BeanUtils.copyProperties(questionDto,q);
        q.setGmtCreate(System.currentTimeMillis());
        int i = questionMapper.updateById(q);
        if(i!=1){
            //多处需要判断 要封装一个枚举
            throw new QuestionException(QuestionErrorCodeEnum.QUESTION_NOT_FOUND);
        }
        return q;
    }

    @Override
    public void insviewCount(Long id) {
       
    }

    @Override
    public List<Question> selTagrealted(QuestionDto questiondto) {
        Question selQuestion = questionMapper.selectById(questiondto.getId());
        String tag = selQuestion.getTag();
        //输入法中中文与英文， 在分割中存在区别 需要两次检验 第二次不能用tag s失败会返回原字符串 前一次正则失效。
        String s = tag.replaceAll(",", "|");
        String s1 = s.replaceAll("，", "|");
        Question question=new Question();
        question.setTag(s);
        question.setId(questiondto.getId());
        List<Question> questions = questionMapper.selQuestionTag(question);
        return questions;
    }
}
