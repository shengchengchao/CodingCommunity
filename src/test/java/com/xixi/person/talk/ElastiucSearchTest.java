package com.xixi.person.talk;


import com.xixi.person.talk.Es.QuestionInfo;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ElastiucSearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testQueryBuilder() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "spring");
        String s = new SearchSourceBuilder().query(termQueryBuilder).toString();
        System.out.println(s);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQueryBuilder).withPageable(PageRequest.of(1,3)).build();
        List<QuestionInfo> questionInfos = elasticsearchTemplate.queryForList(searchQuery,QuestionInfo.class);
        questionInfos.forEach(each->{
            System.out.println(each.toString());
        });

    }

}
