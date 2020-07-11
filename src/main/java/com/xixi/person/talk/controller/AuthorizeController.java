package com.xixi.person.talk.Controller;

import com.alibaba.fastjson.JSONObject;
import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.dto.AccessTokendto;
import com.xixi.person.talk.dto.GithubUserdto;
import com.xixi.person.talk.model.User;
import com.xixi.person.talk.utils.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Author: xixi-98
 * @Date: 2019/12/15 20:11
 * @Description:
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Resource
    private UserService userService;

    @Value("${AccessTokendto.Client_id}")
    private String clientId;
    @Value("${AccessTokendto.Client_secret}")
    private String clientSecret;
    @Value("${AccessTokendto.Redirect_uri}")
    private String redirectUri;

    /**
    * @Description:  站点得到github回传的信息，包含code与stateAccessTokendto
     * 用来装配站点再次访问github中需要的数据。可以考虑使用软编码配合构造方法去完成对应内容的注入
     *  将对象注入到作为参数传递为githubProvider 进行模拟浏览器客户端的post请求。  githubProvider返回一个accesstoken的信息
     *  将信息传递给githubProvider 调用方法模拟浏览器客户端的get请求，获得用户信息
    * @Param:  code 授权码 state
    * @return: index.html 返回初始登录界面
    * @Author: xixi
    * @Date: 2019/12/16
    */
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){

        AccessTokendto accessTokendto = new AccessTokendto(clientId,clientSecret,code,redirectUri,state);

        String accessToken = githubProvider.getAccessToken(accessTokendto);
        GithubUserdto githubuser = githubProvider.getUser(accessToken);

        //将获得到的githubuser中信息 传入到users对象中，存放到数据库中，进行持久化操作。
        if(githubuser != null && !"".equals(githubuser)){
            String token = UUID.randomUUID().toString();
            User user = new User();

            user.setAvatarUrl(githubuser.getAvatarUrl());
            user.setAccountId(githubuser.getId());
            user.setName(githubuser.getName());
            user.setToken(token);
            user.setCreateDate(System.currentTimeMillis());
            user.setUpdateDate(user.getCreateDate());
            user.setBio(githubuser.getBio());
            userService.saveorupd(user);
            //存放到cookies中
            Cookie cookie=new Cookie("token",token);
            response.addCookie(cookie);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);

            return "redirect:/index";
        }else{
            return "redirect:/index";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,HttpSession session) {
        User user = (User) session.getAttribute("user");
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/index";
    }


}
