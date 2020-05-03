package com.xixi.person.talk.EsProvider;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xixi.person.talk.dto.QuestionInfo;
import com.xixi.person.talk.dto.datePage;
import com.xixi.person.talk.model.Question;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Description : 数据提供者
 * @ Author :  shengchengchao
 * @ Date :  2020/4/28
 */
@Component
public class questionProvider implements basicProvider<datePage<QuestionInfo>> {
    @Resource
    private com.xixi.person.talk.mapper.QuestionMapper questionMapper;

    @Override
    public void addElement(datePage<QuestionInfo> target) {

        int current = target.getCurrent();

        int dealCount = target.getDealCount();

        while(current*target.getSize() <= target.getLimitCount()*(dealCount+1)){
            Page<Question> page =new Page<>(current,target.getSize());
            QueryWrapper<Question> qw =new QueryWrapper<>();
            IPage<Question> questionIPage = questionMapper.selectPage(page, qw);
            List<QuestionInfo> questionInfos = new ArrayList<>();
            for (Question q : questionIPage.getRecords()) {
                QuestionInfo questionInfo = new QuestionInfo();
                BeanUtils.copyProperties(q,questionInfo);
                questionInfos.add(questionInfo);
            }
            current++;
            target.setCurrent(current);
            target.setRecord(questionInfos);
        }
        target.setDealCount(dealCount+1);
    }
}
