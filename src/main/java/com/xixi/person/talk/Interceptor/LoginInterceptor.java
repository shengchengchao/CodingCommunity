package com.xixi.person.talk.Interceptor;


import com.xixi.person.talk.Service.NotificationService;
import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xixi-98
 * @Date: 2019/12/20 12:36
 * @Description: 拦截器 对于用户是否登陆进行验证
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private NotificationService notificationServiceImpl;
    @Resource
    private UserService userServiceImpl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User)request.getSession().getAttribute("user");
        Integer unreadCount = (Integer)request.getSession().getAttribute("unreadCount");
        if(user!=null && !"".equals(user) && unreadCount!= null ){
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                        user = userServiceImpl.selUser(cookie.getValue());
                        if(user!=null){
                            request.getSession().setAttribute("user",user);
                            unreadCount = notificationServiceImpl.unreadCount(user.getAccountId());
                            request.getSession().setAttribute("unreadCount", unreadCount);
                        }
                    break;
                }
            }
        }
        return true;
    }
}
