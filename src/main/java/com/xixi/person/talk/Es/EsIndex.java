package com.xixi.person.talk.Es;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ Description :ES操作基本类
 * @ Author :  shengchengchao
 * @ Date :  2020/4/28
 */
@Component
@Slf4j
public abstract class EsIndex<T> {

    Class<T> tClass;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * @Description: 获取类型的泛型
     * @Author: shengchengchao
     * @Date: 2020/4/28
     * @param:
     * @return:
     **/
    public EsIndex() {
        ParameterizedType genericSuperclass = (ParameterizedType)this.getClass().getGenericSuperclass();
        tClass=(Class<T>)genericSuperclass.getActualTypeArguments()[0];

    }
    /**
     * @Description: 初始化索引
     * @Author: shengchengchao
     * @Date: 2020/4/28
     * @param:
     * @return:
     **/
    public boolean InitIndex(){
        createIndex();
        addOrUpdateMapping();
        return true;
    }



    /**
     * @Description: 创建对应索引
     * @Author: shengchengchao
     * @Date: 2020/4/28
     * @param:
     * @return:
     **/
    public boolean createIndex (){
        if(!elasticsearchTemplate.indexExists(tClass)){
            log.debug("没有对应索引，创建索引");
        }else{
            elasticsearchTemplate.createIndex(tClass);
        }
        return true;
    }

    /**
     * @Description: 创建映射
     * @Author: shengchengchao
     * @Date: 2020/4/28
     * @param:
     * @return:
     **/
    public boolean addOrUpdateMapping(){
        log.debug("开始创建Mapping");
        return elasticsearchTemplate.putMapping(tClass);
    }


    /**
     * @Description: 批量新增数据 使用Bulk 来加快新增数据
     * @Author: shengchengchao
     * @Date: 2020/4/28
     * @param:
     * @return:
     **/
    public boolean putDate(List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        List<IndexQuery> queries = new ArrayList<>();
        list.forEach(each->queries.add(new IndexQueryBuilder().withObject(each).build()));
        elasticsearchTemplate.bulkIndex(queries);

        return true;
    }
    /**
     * @Description: 新增单条数据
     * @Author: shengchengchao
     * @Date: 2020/4/28
     * @param:
     * @return:
     **/
    public boolean putOne(T date){
        elasticsearchTemplate.index(new IndexQueryBuilder().withObject(date).build());
        return true;
    }



}
