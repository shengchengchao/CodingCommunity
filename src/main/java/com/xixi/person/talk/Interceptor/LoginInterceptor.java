package com.xixi.person.talk.Interceptor;

import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/20 12:36
 * @Description: 拦截器 对于用户是否登陆进行验证
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userServiceImpl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        User user=new User();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                user = userServiceImpl.selUser(token);
                if(user != null && !user.equals("")){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return true;
    }
}
