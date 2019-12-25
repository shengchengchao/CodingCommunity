package com.xixi.person.talk.Enum;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/24 20:46
 * @Description:
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private int type;

    public int getType() {
        return type;
    }

    CommentTypeEnum(int type){
        this.type=type;
    }

    public static  boolean isExist(int type){
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType()==type){
                return true;
            }
        }
        return false;
    }

}
