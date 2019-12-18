package com.xixi.pereson.talk.Controller;

import com.xixi.pereson.talk.Model.Question;
import com.xixi.pereson.talk.Model.Users;
import com.xixi.pereson.talk.Service.QuestionService;
import com.xixi.pereson.talk.Service.UserService;
import com.xixi.pereson.talk.mapper.QuestionMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/17 13:19
 * @Description:
 */
@Controller
public class PublishController {
    @Resource
    private UserService userServiceImpl;
    @Resource
    private QuestionService questionServiceImpl;
    @GetMapping("/publish")
    public String publish(){


        return "publish";
    }

    /**
    * @Description: 将获得的数据插入到数据库表question中，先进行了一个非空验证，然后有一个登录校验，
     * 需要进行重构，使用表单校验注解优化非空校验，使用拦截器等，完成用户校验 在插入数据时，create是使用user表的accountid字段
    * @Param:
    * @return:
    * @Author: xixi
    * @Date: 2019/12/17
    */
    @PostMapping("/publish")
    public  String questionController(@RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "description", required = false) String description,
                                      @RequestParam(value = "tag", required = false) String tag,
                                      Model model, HttpServletRequest request, HttpSession session){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null ||description.equals("")){
            model.addAttribute("error","内容补充不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        Users user=new Users();
        Cookie[] cookies = request.getCookies();
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
        if(user != null && !user.equals("")){

            Question question=new Question();
            question.setTitle(title);
            question.setCreatorid(user.getAccountid());
            question.setDescription(description);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setTag(tag);

            questionServiceImpl.insquestion(question);
        }else{
            model.addAttribute("error","用户未登录");
            return "redirect:/index";
        }
        return "redirect:/index";
    }

}
