package com.xixi.person.talk.Interceptor;

import com.alibaba.fastjson.JSON;
import com.xixi.person.talk.Service.NotificationService;
import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.dto.TagDTO;
import com.xixi.person.talk.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/20 12:36
 * @Description: 拦截器 对于用户是否登陆进行验证
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private NotificationService notificationServiceImpl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    String userStr = (String)redisTemplate.opsForValue().get(token);
                    User user = JSON.parseObject(userStr, User.class);
                    if(user != null && !user.equals("")){
                        request.getSession().setAttribute("user",user);
                        Integer unreadCount = (Integer) redisTemplate.opsForValue().get("unreadCount");
                        if (unreadCount == null){
                            unreadCount = notificationServiceImpl.unreadCount(user.getAccountId());
                            redisTemplate.opsForValue().set("unreadCount",unreadCount);
                        }
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }
}
