package com.xixi.person.talk.Controller;

import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.dto.PaginationDto;
import com.xixi.person.talk.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/19 22:33
 * @Description:
 */
@Controller
public class ProfileController {
    @Resource
    private UserService userServiceImpl;
    @Resource
    private QuestionService questionServiceImpl;
    
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
        if (user == null || user.equals("")){
            return "index";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals("action")){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最近回复");
        }

        PageInfo pageInfo=questionServiceImpl.selQuestionList(user.getAccountId(),size,page);
        model.addAttribute("pageInfo",pageInfo);
        return "profile";
    }
}
