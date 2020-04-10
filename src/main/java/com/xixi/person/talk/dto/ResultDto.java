package com.xixi.person.talk.dto;

import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.exception.QuestionException;

import java.io.Serializable;

/**
 * @Author: xixi-98
 * @Date: 2019/12/24 20:18
 * @Description:
 */
public class ResultDto<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    public static ResultDto errorof(int code,String message){
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);

        return resultDto;
    }
    public static ResultDto errorof(QuestionErrorCodeEnum errorCode) {
        return errorof(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDto errorof(QuestionException e) {
        return errorof(e.getCode(),e.getMessage());
    }


    public static ResultDto okof(){
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("成功，success");
        return resultDto;
    }
    public static <T> ResultDto okOf(T t) {
        ResultDto resultDTO = new ResultDto();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T date) {
        this.data = date;
    }
}
