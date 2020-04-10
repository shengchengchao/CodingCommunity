package com.xixi.person.talk.exception;

/**
 * @Autheo: xixi-98
 * @Date: 2019/12/22 22:08
 * @Description:
 */
public enum QuestionErrorCodeEnum implements MyErrorCode{
    /** 异常信息 */
    QUESTION_NOT_FOUND(2000,"你找到问题不在了，要不要换个试试？"),
    USER_NOT_FOUND(2001,"抱歉，您未登录，请重新登录"),
    QUESTION_NOT_SELECT(2002,"未选中该问题，请重新选择"),
    TYPE_NOT_SELECT(2003,"评论类型不对,请重新选择"),
    COMMENT_NOT_SELECT(2004,"评论未被选中,请重新选择"),
    SYS_ERROR(2005,"系统错误"),
    COMMENT_IS_EMPTY(2006,"评论不能为空"),
    READ_NOTIFICATION_FAIL(2007, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2008, "消息莫非是不翼而飞了？");


    private String message;
    private int code;
    QuestionErrorCodeEnum(int code,String message) {
        this.code=code;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public int getCode() {
        return code;
    }
}
