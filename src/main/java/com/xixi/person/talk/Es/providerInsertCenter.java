package com.xixi.person.talk.Es;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xixi.person.talk.mapper.QuestionMapper;
import com.xixi.person.talk.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
    @Resource
    private QuestionMapper questionMapper;


    /**
     * @Description: 插入所有数据
     * @Author: shengchengchao
     * @Date: 2020/5/3
     * @param:
     * @return:
     **/
    public void executeIndex(){
        questionIndex.InitIndex();
        // 添加对应的数据提供者。
        add(questionProvider);
        List<Question> questions = questionMapper.selectList(new QueryWrapper<>());
        List<QuestionInfo> listInfo = new ArrayList<>();
        questions.forEach(each->{
            QuestionInfo qw = new QuestionInfo();
            BeanUtils.copyProperties(each,qw);
            listInfo.add(qw);
        });
        questionIndex.putDate(listInfo);
    }

    public void putOrUpd(Question q){
        QuestionInfo info =new QuestionInfo();
        BeanUtils.copyProperties(q,info);
        questionIndex.putOne(info);
    }


}
