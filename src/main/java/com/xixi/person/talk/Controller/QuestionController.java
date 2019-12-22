package com.xixi.person.talk.Controller;

import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/20 16:21
 * @Description:
 */
@Controller
public class QuestionController {
    @Resource
    private QuestionService questionServiceImpl;
    
    /**
    * @Description: 查询出指定id的问题 并跳转publish页面进行修改操作
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/20
    */
    @GetMapping("/question/{id}")
    public String question(QuestionDto question, HttpSession session, Model model){

        User user = (User) session.getAttribute("user");
        if (user == null || user.equals("")){
            return "index";
        }
        QuestionDto questionDto=questionServiceImpl.selQuestionByid(question.getId());
        model.addAttribute("questionDto",questionDto);
        return "question";
    }
}
