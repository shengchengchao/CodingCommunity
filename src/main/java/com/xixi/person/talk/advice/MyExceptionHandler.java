package com.xixi.person.talk.advice;

import com.alibaba.fastjson.JSON;
import com.xixi.person.talk.dto.ResultDto;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.exception.QuestionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: xixi-98
 * @Date: 2019/12/22 21:30
 * @Description:
 */
@ControllerAdvice
public class MyExceptionHandler {
    //ExceptionHandler 我的exception类 没有可用jdk的
    @ExceptionHandler(Exception.class)
    ModelAndView handler(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response){
       //先对类型进行判断 是json还是html类型
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //json
            ResultDto resultDto;
            if(e instanceof QuestionException){
                resultDto=ResultDto.errorof((QuestionException)e);
            }else {
                resultDto=ResultDto.errorof(QuestionErrorCodeEnum.SYS_ERROR);
            }
            try {
                response.setStatus(200);
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.print(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e1) {
            }
            return null;

        }else {
            //html
            if (e instanceof QuestionException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", (QuestionErrorCodeEnum.SYS_ERROR).getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
