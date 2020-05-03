package com.xixi.person.talk.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.model.Question;
import com.xixi.person.talk.schedule.TagTasks;
import com.xixi.person.talk.utils.Pageutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
* @Description: 出口控制器
* @Param: 
* @return: 
* @Author: xixi
* @Date: 2020/2/14
*/
@Controller
public class IndexController {

    @Resource
    private UserService userServiceImpl;
    @Resource
    private QuestionService questionServiceImpl;
    @Autowired
    private TagTasks tagTasks;

    /**
    * @Description: 登录的第一个controller
    * @Param: null
    * @return:  登录界面index.html
    * @Author: xixi
    * @Date: 2019/12/15
    */
    @GetMapping("/index")
    public  String login( HttpSession session, Model model,
                         @RequestParam(defaultValue = "3") int size,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "",name = "search", required = false) String search,
                         @RequestParam(defaultValue = "",name = "tag", required = false) String tag,
                          @RequestParam(defaultValue = "new",name = "sort", required = false) String sort){
        Long id=0L;
        //查询出当前页数据，填充列表数据
        Pageutils pageList;
        pageList = questionServiceImpl.selQuestionList(id,size, page,search,tag,sort);
        model.addAttribute("pageInfo",pageList);
        model.addAttribute("tag",tag);
        model.addAttribute("search",search);
        model.addAttribute("sort",sort);
        model.addAttribute("tags",tagTasks.setHotTag());
        return "index";
    }

}
