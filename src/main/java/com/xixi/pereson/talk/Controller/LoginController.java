package com.xixi.pereson.talk.Controller;

import com.sun.deploy.net.HttpResponse;
import com.xixi.pereson.talk.Model.Users;
import com.xixi.pereson.talk.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Resource
    private UserService userServiceImpl;
    /**
    * @Description: 登录的第一个controller
    * @Param: null
    * @return:  登录界面index.html
    * @Author: xixi
    * @Date: 2019/12/15
    */
    @GetMapping("/index")
    public  String login(HttpServletRequest request, HttpSession session){

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                Users user = userServiceImpl.selUser(token);
                if(user != null && !user.equals("")){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";
    }

}
