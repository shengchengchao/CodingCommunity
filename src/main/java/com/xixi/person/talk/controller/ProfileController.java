package com.xixi.person.talk.controller;

import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.Service.NotificationService;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: xixi-98
 * @Date: 2019/12/19 22:33
 * @Description:
 */
@Controller
public class ProfileController {
    @Resource
    private UserService userServiceImpl;
    @Resource
    private QuestionService questionServiceImpl;
    @Resource
    private NotificationService notificationServiceImpl;
    
    /**
    * @Description: 显示出我的所有问题
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/20
    */
    @RequestMapping("/profile/{action}")
    public String profile(@PathVariable(name="action")String action,
                           HttpSession session, Model model,
                          @RequestParam(defaultValue = "3") int size,
                          @RequestParam(defaultValue = "1") int page){

        User user = (User) session.getAttribute("user");
        if (user == null || "".equals(user)){
            return "redirect:/index";
        }
        if("question".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            String search="";
            String tag="";
            String sort="";
            PageInfo pageInfo= null;
            try {
                pageInfo = questionServiceImpl.selQuestionList(user.getAccountId(),size,page,search,tag, sort);
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("pageInfo",pageInfo);
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最近回复");
            PageInfo pageInfo= notificationServiceImpl.selNotificationList(user.getAccountId(),size,page);
            model.addAttribute("pageInfo",pageInfo);
        }
        return "profile";
    }
}
