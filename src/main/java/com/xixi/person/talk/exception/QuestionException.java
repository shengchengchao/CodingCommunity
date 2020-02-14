package com.xixi.person.talk.exception;

/**
 * @Author: xixi-98
 * @Date: 2019/12/22 21:41
 * @Description: 自定义的异常
 */
public class QuestionException extends RuntimeException {

    private String message;
    private int code;

    public QuestionException(MyErrorCode error) {
        this.code=error.getCode();
        this.message = error.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
