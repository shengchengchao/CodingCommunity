package com.xixi.person.talk.center;

import com.xixi.person.talk.EsProvider.basicProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class providerCenter<T> {
    List<basicProvider<T>> list =new ArrayList<>();
    /**
     * @ Description :  放入数据提供者
     * @ Author :  shengchengchao
     * @ Date :  2020/4/28 
     */
    public void add(basicProvider<T> provider){
        list.add(provider);
    }


    public void buildDate(T target){
        list.forEach(each->{
            try {
                each.addElement(target);
            }catch (Exception e){
                log.error("数据报错", e);
            }
        });

    }
}
