package com.xixi.person.talk.advice;

import com.xixi.person.talk.exception.QuestionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/22 21:30
 * @Description:
 */
@ControllerAdvice
public class MyExceptionHandler {
    //ExceptionHandler 我的exception类 没有可用jdk的
    @ExceptionHandler(Exception.class)
    ModelAndView handler(Throwable e, Model model){

        if (e instanceof QuestionException) {
            //出错了 传递参数
            model.addAttribute("message", e.getMessage());
        } else {
            //未知错误
            model.addAttribute("message", "服务冒烟了，要不然你稍后再试试！！！");
        }
        //返回erroe页面
        return new ModelAndView("error");
    }


}
