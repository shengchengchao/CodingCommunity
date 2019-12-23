package com.xixi.person.talk.Controller;

import com.xixi.person.talk.Service.QuestionService;

import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/17 13:19
 * @Description:
 */
@Controller
public class PublishController {

    @Resource
    private QuestionService questionServiceImpl;
    
    /**
    * @Description: 初始发布问题页面
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/21
    */
    @GetMapping("/publish")
    public String publish(Model model,QuestionDto questionDto){

        model.addAttribute("questionDto",questionDto);

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
    public  String questionController(QuestionDto questionDto,
                                      Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

        model.addAttribute("title",questionDto.getTitle());
        model.addAttribute("description",questionDto.getDescription());
        model.addAttribute("tag",questionDto.getTag());

        if (questionDto.getTitle() == null || questionDto.getTitle().equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (questionDto.getDescription() == null ||questionDto.getDescription().equals("")){
            model.addAttribute("error","内容补充不能为空");
            return "publish";
        }
        if (questionDto.getTag() == null || questionDto.getTag().equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        //先判断用户是否进行了登录 未登录就显示错误
        User user = (User) session.getAttribute("user");
        if(user != null && !user.equals("")){
            if(questionDto.getId() != null && !questionDto.getId().equals("")){
                // 将使用id查询出的QuestionDto 与session中的用户id进行比较  防止 非提问者对问题进行篡改 跳转index页面 使cookies失效
                QuestionDto selectQuestion = questionServiceImpl.selQuestionByid(questionDto.getId());
                if (user.getAccountId().equals(selectQuestion.getCreatorId())){
                    questionServiceImpl.updateQuestion(questionDto);
                }else{
                    model.addAttribute("error","请重新登录");
                    request.getSession().removeAttribute("user");
                    Cookie cookie = new Cookie("token", null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }else{
                questionDto.setUser(user);
                questionServiceImpl.insquestion(questionDto);
            }
        }else{
            model.addAttribute("error","用户未登录，请点击登录");
            return "publish";
        }
        return "redirect:/index";
    }

}
