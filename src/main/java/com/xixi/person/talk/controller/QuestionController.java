package com.xixi.person.talk.Controller;

import com.xixi.person.talk.Service.CommentService;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.enums.CommentTypeEnum;
import com.xixi.person.talk.dto.CommentDto;
import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: xixi-98
 * @Date: 2019/12/20 16:21
 * @Description:
 */
@Controller
public class QuestionController {
    @Resource
    private QuestionService questionServiceImpl;
    @Resource
    private CommentService commentServiceImpl;
    /**
    * @Description: 查询出指定id的问题 并跳转publish页面进行修改操作
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/20
    */
    @GetMapping("/question/{id}")
    public String question(QuestionDto question, HttpSession session, Model model){
        //得到问题的信息
        QuestionDto questionDto=questionServiceImpl.selQuestionByid(question.getId());
        //增加阅读数
        questionServiceImpl.insviewCount(question.getId());
        //获得该问题下的评论
        List<CommentDto> commentDtos = commentServiceImpl.selCommentList(question.getId(), CommentTypeEnum.QUESTION);
        //获得与该问题标签相似的问题
        List<Question> tagQuestion = questionServiceImpl.selTagrealted(question);
        model.addAttribute("relatedQuestions",tagQuestion);
        model.addAttribute("questionDto",questionDto);
        model.addAttribute("comments",commentDtos);
        return "question";
    }
}
