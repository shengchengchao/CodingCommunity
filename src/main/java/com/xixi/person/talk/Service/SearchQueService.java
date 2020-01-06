package com.xixi.person.talk.Service;

import com.xixi.person.talk.dto.QuestionDto;
import com.xixi.person.talk.dto.SearchDto;
import com.xixi.person.talk.model.Question;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2020/1/5 09:36
 * @Description:
 */

public interface SearchQueService {
    
    /**
    * @Description: 完成mysql到es的数据添加
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2020/1/5
    */
    void put(Question question);
    
    /**
    * @Description:
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2020/1/5
    */
    List<SearchDto> resultList(String search) throws IOException;

    void putall();

}
