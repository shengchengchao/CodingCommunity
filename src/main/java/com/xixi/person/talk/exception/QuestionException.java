package com.xixi.person.talk.exception;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/22 21:41
 * @Description: 自定义的异常
 */
public class QuestionException extends RuntimeException {

    private String message;

    public QuestionException(MyErrorCode error) {
        this.message = error.getMessage();
    }

    public QuestionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
