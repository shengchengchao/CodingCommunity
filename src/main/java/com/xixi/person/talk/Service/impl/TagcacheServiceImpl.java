package com.xixi.person.talk.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.xixi.person.talk.Service.TagcacheService;
import com.xixi.person.talk.dto.TagDTO;
import com.xixi.person.talk.mapper.TagcacheMapper;
import com.xixi.person.talk.model.Tagcache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TagcacheServiceImpl extends ServiceImpl<TagcacheMapper, Tagcache> implements TagcacheService {

    @Resource
    private TagcacheMapper tagcacheMapper;

    @Override
    public List<TagDTO> get() {
        //能够从redis中查询出标签

        List<TagDTO> tagDtos = new ArrayList<>();
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

        tagDtos.add(program);
        tagDtos.add(framework);
        tagDtos.add(server);
        tagDtos.add(db);
        tagDtos.add(tool);
        // 将集合转为json字符串放入redis中
        String tagDtoStr = JSONObject.toJSONString(tagDtos);
        return tagDtos;
    }

    /**
     * @Description: 封装 返回该组别的tag
     * @Param: Chindesc 中文描述  Engdesc 英文描述
     * @return:
     * @Author: xixi
     * @Date: 2020/2/4
     */
    public TagDTO getTag(TagDTO dto,String ChinaDesc,String EngDesc){
        dto.setCategoryName(ChinaDesc);
        List<Tagcache> tagcaches = new LambdaQueryChainWrapper<Tagcache>(tagcacheMapper).eq(Tagcache::getCache, EngDesc).list();
        List<String> tag=new ArrayList<>();
        for (Tagcache tagcach : tagcaches) {
            tag.add(tagcach.getName());
        }
        dto.setTags(tag);
        return dto;
    }

    @Override
    public String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDtos = get();
        // java8 stream 流
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
