package com.xixi.person.talk.Controller;

import com.xixi.person.talk.Service.SearchQueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Auther: xixi-98
 * @Date: 2020/2/5 14:29
 * @Description: 帮助es 将mysql数据进行迁移
 */
@Controller
public class SearchinitController {
    @Resource
    private SearchQueService searchQueServiceImpl;
    @RequestMapping("/question/init")
    public void init(){
        searchQueServiceImpl.putall();
    }
}
