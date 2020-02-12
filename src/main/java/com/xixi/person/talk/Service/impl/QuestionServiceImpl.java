package com.xixi.person.talk.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.Service.SearchQueService;
import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.dto.SearchDto;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.exception.QuestionException;
import com.xixi.person.talk.mapper.QuestionMapper;
import com.xixi.person.talk.mapper.QuestionextraMapper;
import com.xixi.person.talk.mapper.UserMapper;
import com.xixi.person.talk.model.Question;
import com.xixi.person.talk.model.QuestionExample;
import com.xixi.person.talk.model.User;
import com.xixi.person.talk.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
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

   @Resource
   private QuestionextraMapper questionextraMapper;
   @Resource
   private SearchQueService searchQueServiceImpl;

    @Override
    @Transactional
    public Question insquestion(QuestionDto questionDto) {
        Question question=new Question();
        BeanUtils.copyProperties(questionDto,question);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreatorId(questionDto.getUser().getAccountId());
        questionMapper.insertSelective(question);
        QuestionExample example = new QuestionExample();
        BeanUtils.copyProperties(question,example);
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBs(example);
        return questions.get(0);
    }

    @Override
    @Transactional
    public PageInfo selQuestionList(Long accountId,int size, int page,String search) throws IOException {
        List<SearchDto> searchDtoList = null;
        if(StringUtils.isNotBlank(search)&&!search.equals(",") ) {
            searchDtoList=searchQueServiceImpl.resultList(search);
        }
        if(searchDtoList != null && !searchDtoList.equals("")) {

            List<QuestionDto> questionDtoList = new ArrayList<>();
            for (SearchDto searchDto : searchDtoList) {
                QuestionDto questionDto = new QuestionDto();
                BeanUtils.copyProperties(searchDto, questionDto);
                UserExample example = new UserExample();
                example.createCriteria().andAccountIdEqualTo(searchDto.getCreatorId());
                List<User> users = userMapper.selectByExample(example);
                questionDto.setUser(users.get(0));
                questionDtoList.add(questionDto);
            }
            PageHelper.startPage(page, size);
            PageInfo pageInfo = new PageInfo(questionDtoList, 5);
            return pageInfo;
        }else {
            //查询出当前页 问题
            PageHelper.startPage(page, size);
            QuestionExample quesionExample = new QuestionExample();
            quesionExample.setOrderByClause("gmt_create desc");
            //判断accountId是否存在
            if (accountId != 0L) {
                quesionExample.createCriteria().andCreatorIdEqualTo(accountId);
            }
            //问题总数
            List<Question> questions = questionMapper.selectByExampleWithBLOBs(quesionExample);
            //查询出当前页问题，navugatepage是导航页
            PageInfo pageInfo = new PageInfo(questions, 5);
            List<QuestionDto> list = new ArrayList();
            List pageInfoList = pageInfo.getList();
            for (Object o : pageInfoList) {
                QuestionDto questionDto = new QuestionDto();
                BeanUtils.copyProperties(o, questionDto);
                UserExample userExample = new UserExample();
                Long accountid = ((Question) o).getCreatorId();
                userExample.createCriteria().andAccountIdEqualTo(accountid);
                List<User> users = userMapper.selectByExample(userExample);
                questionDto.setUser(users.get(0));
                list.add(questionDto);
            }
            pageInfo.setList(list);
            return pageInfo;
        }
    }

    @Override
    @Transactional
    public QuestionDto selQuestionByid(Long id) {
        QuestionDto questionDto=new QuestionDto();

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        List<Question> questions = questionMapper.selectByExampleWithBLOBs(questionExample);
        if(questions.size()==0){
            throw new QuestionException(QuestionErrorCodeEnum.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(questions.get(0),questionDto);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(questions.get(0).getCreatorId());
        List<User> users = userMapper.selectByExample(userExample);
        questionDto.setUser(users.get(0));
        return questionDto;
    }

    @Override
    @Transactional
    public Question updateQuestion(QuestionDto questionDto) {
        Question question=new Question();
        BeanUtils.copyProperties(questionDto,question);
        question.setGmtModified(System.currentTimeMillis());
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(question.getId());
        int update = questionMapper.updateByExampleSelective(question, questionExample);
        if(update != 1){
            //多处需要判断 要封装一个枚举
            throw new QuestionException(QuestionErrorCodeEnum.QUESTION_NOT_FOUND);
        }
        questionExample.setOrderByClause("gmt_modified desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBs(questionExample);
        return questions.get(0);
    }

    @Transactional
    public void insviewCount(Long id){
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionextraMapper.updatevicwConunt(question);
    }

    @Override
    public List<Question> selTagrealted(QuestionDto questionDto) {
        Question selQuestion = questionMapper.selectByPrimaryKey(questionDto.getId());
        String tag = selQuestion.getTag();
        //输入法中中文与英文， 在分割中存在区别 需要两次检验 第二次不能用tag s失败会返回原字符串 前一次正则失效。
        String s = tag.replaceAll(",", "|");
        String s1 = s.replaceAll("，", "|");
        Question question=new Question();
        question.setTag(s);
        question.setId(questionDto.getId());
        List<Question> questions = questionextraMapper.selQuestionTag(question);
        return questions;
    }


}
