package com.xixi.person.talk.dto;

import java.io.Serializable;

/**
 * @Author: xixi-98
 * @Date: 2020/2/14 19:14
 * @Description:
 */
public class HotTagDto implements Comparable,Serializable {
    private String name;
    private Integer priority;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    @Override
    public int compareTo(Object o) {
        return  this.getPriority() - ((HotTagDto) o).getPriority();
    }
}
