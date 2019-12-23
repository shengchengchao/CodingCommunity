package com.xixi.person.talk.exception;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/22 22:08
 * @Description:
 */
public enum QuestionErrorCodeEnum implements MyErrorCode{

    QUESTION_NOT_FOUND("你找到问题不在了，要不要换个试试？");

    private String message;

    QuestionErrorCodeEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
