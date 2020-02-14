package com.xixi.person.talk.Service.impl;

import com.xixi.person.talk.Service.SearchQueService;
import com.xixi.person.talk.dto.SearchDto;
import com.xixi.person.talk.Mapper.QuestionMapper;

import com.xixi.person.talk.Model.Question;
import com.xixi.person.talk.Model.QuestionExample;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2020/1/5 09:41
 * @Description:
 */
@Service
public class SearchQueServiceImpl implements SearchQueService {
    @Resource
    private QuestionMapper questionMapper;

    @Autowired
    private JestClient jestClient;
    
    /**
    * @Description: 添加单条数据
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2020/1/6
    */
    @Override
    public void put(Question question) {
        //mysql转化为es的结构
        SearchDto searchDto=new SearchDto();
        BeanUtils.copyProperties(question,searchDto);
        //导入数据
        String id = String.valueOf(searchDto.getId());
        Index put = new Index.Builder(searchDto).index("community").type("question").id(id).build();
        try {
            jestClient.execute(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 添加所有数据
     * @Param:
     * @return:
     * @Author: xixi
     * @Date: 2020/1/6
     */
    @Override
    public void putall() {
        List<Question> questions = questionMapper.selectByExampleWithBLOBs(new QuestionExample());

        List<SearchDto> list=new ArrayList<>();
        for (Question question : questions) {
            //mysql转化为es的结构
            SearchDto searchDto=new SearchDto();
            BeanUtils.copyProperties(question,searchDto);
            //导入数据
            String id = String.valueOf(searchDto.getId());
            Index put = new Index.Builder(searchDto).index("community").type("question").id(id).build();
            try {
                jestClient.execute(put);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * @Description: 搜索时返回所有相关数据
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2020/1/5
    */
    @Override
    public List<SearchDto> resultList(String search) throws IOException {
        List<SearchDto> list=new ArrayList<>();
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        MatchQueryBuilder matchQueryBuilder1= QueryBuilders.matchQuery("title",search);
        boolQueryBuilder.must(matchQueryBuilder1);
        sourceBuilder.query(boolQueryBuilder);
        Search build = new Search.Builder(sourceBuilder.toString()).addIndex("community").addType("question").build();
        SearchResult execute =jestClient.execute(build);
        List<SearchResult.Hit<SearchDto, Void>> hits = execute.getHits(SearchDto.class);
        for (SearchResult.Hit<SearchDto, Void> hit : hits) {
            SearchDto source = hit.source;
            list.add(source);
        }
        return list;
    }
}
