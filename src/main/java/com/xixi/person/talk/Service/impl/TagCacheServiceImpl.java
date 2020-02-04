package com.xixi.person.talk.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xixi.person.talk.Service.TagCacheService;
import com.xixi.person.talk.dto.TagDTO;
import com.xixi.person.talk.mapper.TagcacheMapper;
import com.xixi.person.talk.model.Tagcache;
import com.xixi.person.talk.model.TagcacheExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: xixi-98
 * @Date: 2020/2/4 14:17
 * @Description:
 */
@Service
public class TagCacheServiceImpl implements TagCacheService {
    @Resource
    private TagcacheMapper tagcacheMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<TagDTO> get() {
        //能够从redis中查询出标签
        String obj = (String)redisTemplate.opsForValue().get("tagDTOS");
        List<TagDTO> tagDTOSCache = JSON.parseArray(obj, TagDTO.class);
        if(tagDTOSCache!=null){
            return tagDTOSCache;
        }
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program = getTag(program, "开发语言", "program");
        TagDTO framework = new TagDTO();
        framework = getTag(framework, "平台框架", "framework");
        TagDTO server = new TagDTO();
        server = getTag(server, "服务器", "server");
        TagDTO db = new TagDTO();
        db = getTag(db, "数据库", "db");
        TagDTO tool = new TagDTO();
        tool = getTag(tool, "开发工具", "tool");

        tagDTOS.add(program);
        tagDTOS.add(framework);
        tagDTOS.add(server);
        tagDTOS.add(db);
        tagDTOS.add(tool);
        // 将集合转为json字符串放入redis中
        String tagDtos = JSONObject.toJSONString(tagDTOS);
        redisTemplate.opsForValue().set("tagDTOS",tagDtos);
        return tagDTOS;

    }

    @Override
    public String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
        // java8 stream 流
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

    
    /**
    * @Description: 封装 返回该组别的tag
    * @Param: Chindesc 中文描述  Engdesc 英文描述
    * @return: 
    * @Author: xixi
    * @Date: 2020/2/4
    */
    public TagDTO getTag(TagDTO dto,String Chindesc,String Engdesc){

        dto.setCategoryName(Chindesc);
        TagcacheExample programTagcache = new TagcacheExample();
        programTagcache.createCriteria().andCacheEqualTo(Engdesc);
        List<Tagcache> tagcaches = tagcacheMapper.selectByExample(programTagcache);
        List<String> tag=new ArrayList<>();
        for (Tagcache tagcach : tagcaches) {
            tag.add(tagcach.getName());
        }
        dto.setTags(tag);
        return dto;
    }
}
