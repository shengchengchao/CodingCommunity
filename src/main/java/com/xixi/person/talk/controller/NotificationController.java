package com.xixi.person.talk.Controller;

import com.xixi.person.talk.Service.NotificationService;
import com.xixi.person.talk.enums.NotificationEnum;
import com.xixi.person.talk.model.Notification;
import com.xixi.person.talk.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author: xixi-98
 * @Date: 2019/12/29 20:15
 * @Description:
 */
@Controller
public class NotificationController {

    @Resource
    private NotificationService notificationServiceImpl;

    @GetMapping("/notification/{id}")
    public String notification(HttpSession session, @PathVariable(name = "id") Long id){
        User user = (User) session.getAttribute("user");
        if (user == null || user.equals("")){
            return "index";
        }
        Notification notification=notificationServiceImpl.read(id,user);
        if(notification.getType()== NotificationEnum.REPLY_QUESTION.getType() ||
                notification.getType()== NotificationEnum.REPLY_COMMENT.getType()){
            return "redirect:/question/" + notification.getOuterid();
        }else {
            return"redirect:/index";
        }

    }

}
