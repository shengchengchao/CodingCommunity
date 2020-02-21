package com.xixi.person.talk.Service;

import com.xixi.person.talk.dto.SearchDto;
import com.xixi.person.talk.Model.Question;

import java.io.IOException;
import java.util.List;

/**
 * @Author: xixi-98
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
    List<SearchDto> resultList(String search, String tag) throws IOException;

    void putall();

}
