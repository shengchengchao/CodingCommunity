package com.xixi.person.talk.center;

import com.xixi.person.talk.EsProvider.questionProvider;
import com.xixi.person.talk.dto.QuestionInfo;
import com.xixi.person.talk.dto.datePage;
import com.xixi.person.talk.index.questionIndex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ Description :  执行ES数据的导入
 * @ Author :  shengchengchao
 * @ Date :  2020/4/28
 */
@Slf4j
public class providerInsertCenter extends providerCenter<datePage<QuestionInfo>> {
    @Autowired
    private questionProvider questionProvider;
    @Autowired
    private questionIndex questionIndex;

    /**
     * @ Description :  初始化线程
     * @ Author :  shengchengchao
     * @ Date :  2020/4/28
     */
    public providerInsertCenter() {
        //返回核心线程数
        int coresize = Runtime.getRuntime().availableProcessors();
        log.debug("当前线程数；{}",coresize);
        new ThreadPoolExecutor(coresize,coresize,100, TimeUnit.MINUTES,new ArrayBlockingQueue<>(100));
    }

    public void executeIndex(){
        questionIndex.InitIndex();
        // 添加对应的数据提供者。
        add(questionProvider);




    }


}
