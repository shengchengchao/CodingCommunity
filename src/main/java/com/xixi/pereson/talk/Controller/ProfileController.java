package com.xixi.pereson.talk.Controller;

import com.xixi.pereson.talk.Model.Users;
import com.xixi.pereson.talk.Service.QuestionService;
import com.xixi.pereson.talk.Service.UserService;
import com.xixi.pereson.talk.dto.PaginationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;

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

    @RequestMapping("/profile/{action}")
    public String profile(@PathVariable(name="action")String action,
                           HttpSession session, Model model,
                          @RequestParam(defaultValue = "3") int size,
                          @RequestParam(defaultValue = "1") int page){

        Users user = (Users) session.getAttribute("user");
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

        PaginationDto profilepaginationDto=questionServiceImpl.selQuestionListByid(user.getAccountid(),size,page);
        model.addAttribute("profilelist",profilepaginationDto.getQuestionList());
        model.addAttribute("profilepaginationDto",profilepaginationDto);
        model.addAttribute("profilepages",profilepaginationDto.getPages());


        return "profile";
    }
}
