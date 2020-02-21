package com.xixi.person.talk.controller;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.github.pagehelper.PageInfo;
import com.xixi.person.talk.Service.QuestionService;
import com.xixi.person.talk.Service.UserService;
import com.xixi.person.talk.schedule.TagTasks;
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
                         @RequestParam(defaultValue = "",name = "tag", required = false) String tag){
        Long id=0L;
        //查询出当前页数据，填充列表数据
        PageInfo pageInfo = null;

        try {
            pageInfo = questionServiceImpl.selQuestionList(id,size, page,search,tag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("tag",tag);
        model.addAttribute("search",search);
        model.addAttribute("tags",tagTasks.setHotTag());
        return "/index";
    }

}
