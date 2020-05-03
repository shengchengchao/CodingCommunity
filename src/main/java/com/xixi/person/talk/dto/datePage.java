package com.xixi.person.talk.dto;

import lombok.Data;

import java.util.List;

@Data
public class datePage<T> {
    /**
     * 当前页数
     */
    private int current;
    /**
     * 总数
     */
    private long totalNum;
    /**
     * 大小
     */
    private int size;
    /**
     * 记录数
     */
    private List<T> record;

    /**
     *处理次数
     */
    private int dealCount;
    /**
     * 一次最大的数据量
     */
    private int LimitCount = 5;


}
