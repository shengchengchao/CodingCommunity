package com.xixi.pereson.talk.Controller;

import com.sun.deploy.net.HttpResponse;
import com.xixi.pereson.talk.Model.Users;
import com.xixi.pereson.talk.Service.QuestionService;
import com.xixi.pereson.talk.Service.UserService;
import com.xixi.pereson.talk.dto.QuestionDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;


@Controller
public class LoginController {

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
    public  String login(HttpServletRequest request, HttpSession session, Model model){
        //先查询是否有cookie信息  有就取出来 在数据库中进行查询，查询后放入到session中 当关闭浏览器后，cookies失效
        Cookie[] cookies = request.getCookies();
        Users user=new Users();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                user = userServiceImpl.selUser(token);
                if(user != null && !user.equals("")){
                    session.setAttribute("user",user);
                }
                break;
            }
        }
        //查询出所有的数据，填充列表数据
        List<QuestionDto> questionLists = questionServiceImpl.selQuestionList();
        model.addAttribute("questions",questionLists);

        return "index";
    }

}
