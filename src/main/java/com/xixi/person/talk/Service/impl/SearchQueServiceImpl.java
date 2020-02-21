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
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<SearchDto> resultList(String search, String tag) throws IOException {
        List<SearchDto> list=new ArrayList<>();
        /*
        *sourceBuilder dsl工具
        * qurey  sourceBuilder.query
        *          bool BoolQueryBuilder
        *          should
        *           filter BoolQueryBuilder.should(termQureryBuilder)
        *           must BoolQueryBuilder.must
        * from sourceBuilder.from() 分页
        *size sourceBuilder.size()
        * highlight sourceBuilder.highlighter() 高亮
        * */
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        if(StringUtils.isNotBlank(search)){
            MatchQueryBuilder matchQueryBuilderTitle= QueryBuilders.matchQuery("title",search);
            MatchQueryBuilder matchQueryBuilderDesc= QueryBuilders.matchQuery("description",search);
            boolQueryBuilder.should(matchQueryBuilderDesc);
            boolQueryBuilder.should(matchQueryBuilderTitle);
        }
        if(StringUtils.isNotBlank(tag)){
            MatchQueryBuilder matchQueryBuilderTag= QueryBuilders.matchQuery("tag",tag);
            boolQueryBuilder.must(matchQueryBuilderTag);
        }
        boolQueryBuilder.minimumShouldMatch(1);
        sourceBuilder.query(boolQueryBuilder);

        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        HighlightBuilder.Field highlighTitle=new HighlightBuilder.Field("title");
        HighlightBuilder.Field highlighdesc=new HighlightBuilder.Field("description");

        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field(highlighTitle);
        highlightBuilder.field(highlighdesc);
        //排序
        sourceBuilder.highlighter(highlightBuilder);
        SortBuilder sortBuilder=SortBuilders.fieldSort("gmtModified").order(SortOrder.DESC);
        sourceBuilder.sort(sortBuilder);

        Search build = new Search.Builder(sourceBuilder.toString()).addIndex("community").addType("question").build();
        SearchResult execute =jestClient.execute(build);
        List<SearchResult.Hit<SearchDto, Void>> hits = execute.getHits(SearchDto.class);
        for (SearchResult.Hit<SearchDto, Void> hit : hits) {
            SearchDto source = hit.source;
            if(StringUtils.isNotBlank(search)){
                Map<String, List<String>> highlight = hit.highlight;
                source.setDescription(highlight.get("description").get(0));
                source.setTitle(highlight.get("title").get(0));
            }

            list.add(source);
        }
        return list;
    }
}
