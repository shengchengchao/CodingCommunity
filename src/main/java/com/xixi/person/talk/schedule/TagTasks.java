package com.xixi.person.talk.schedule;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.xixi.person.talk.dto.HotTagDto;
import com.xixi.person.talk.mapper.QuestionMapper;
import com.xixi.person.talk.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: xixi-98
 * @Date: 2020/2/14 15:49
 * @Description:
 */
@Component
@Slf4j
public class TagTasks {
    @Resource
    private QuestionMapper questionMapper;


    /**
    * @Description:  先遍历所有的question  采用权重公式 priority=question + 3*commentCount + viewCount
     * 得到所有的priorities后 使用优先队列完成topN 小顶堆
    * @Param:
    * @return:
    * @Author: xixi
    * @Date: 2020/2/14
    */
    @Scheduled(cron = "0 0 1 * * *")
    public  List<String> setHotTag(){
        Map<String,Integer> priorities = new HashMap<>();
        List<String> hots = new ArrayList<>();
        List<Question> questions = new LambdaQueryChainWrapper<Question>(questionMapper).list();
        for (Question question : questions) {
            String[] tags = StringUtils.split(question.getTag(), ",");
            for (String tag : tags) {
                Integer priority = priorities.get(tag);
                if(priority!=null){
                    priorities.put(tag,priority+5+3*question.getCommentCount()+question.getViewCount());
                }else{
                    priorities.put(tag,5+3*question.getCommentCount()+question.getViewCount());
                }
            }
        }
        System.out.println(priorities);
        PriorityQueue<HotTagDto> priorityQueue = new PriorityQueue<>(5);
        priorities.forEach((name, priority) -> {
            HotTagDto hotTagDTO = new HotTagDto();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < 5) {
                priorityQueue.add(hotTagDTO);
            } else {
                HotTagDto minHot = priorityQueue.peek();
                if (hotTagDTO.compareTo(minHot) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });
        while (priorityQueue.size()!=0) {
            HotTagDto poll = priorityQueue.poll();
            hots.add(0, poll.getName());
        }
        return hots;
    }
}
