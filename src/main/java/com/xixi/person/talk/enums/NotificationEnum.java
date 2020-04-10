package com.xixi.person.talk.enums;

/**
 * @Author: xixi-98
 * @Date: 2019/12/29 17:09
 * @Description:
 */
public enum NotificationEnum {
    /** 回答了问题*/
    REPLY_QUESTION(1,"回答了问题"),
    /** 回复了评论*/
    REPLY_COMMENT(2,"回复了评论"),
    /** 评论已读*/
    UEREAD(3,"未读"),
    /** 评论未读*/
    READ(4,"已读");

    private int type;
    private String name;

    NotificationEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String getName(int type){
        for (NotificationEnum notificationEnum : NotificationEnum.values()) {
            if(notificationEnum.getType()==type){
                return notificationEnum.getName();
            }
        }
        return "";
    }
}
