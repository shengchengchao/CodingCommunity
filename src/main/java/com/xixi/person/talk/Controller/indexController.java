package com.xixi.person.talk.Controller;

import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
//@RequestMapping("")
public class IndexController {

    @Resource
    private UserService userServiceImpl;
    @Resource
    private QuestionService questionServiceImpl;
    /**
    * @Description: 登录的第一个controller
    * @Param: null
    * @return:  登录界面index.html
    * @Author: xixi
    * @Date: 2019/12/15
    */
    @GetMapping("/index")
    public  String login( HttpSession session, Model model,
                         @RequestParam(defaultValue = "3") int size,
                         @RequestParam(defaultValue = "1") int page){
        String id="";
        //查询出当前页数据，填充列表数据
        PageInfo pageInfo = questionServiceImpl.selQuestionList(id,size, page);
        model.addAttribute("pageInfo",pageInfo);

        return "/index";
    }

}
