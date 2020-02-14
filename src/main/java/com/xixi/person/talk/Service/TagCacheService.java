package com.xixi.person.talk.Service;

import com.xixi.person.talk.dto.TagDTO;

import java.util.List;

/**
 * @Author: xixi-98
 * @Date: 2020/2/4 14:13
 * @Description:
 */
public interface TagCacheService {
    
    /**
    * @Description: 完成tag标签的添加
    * @Param: tag
    * @return: 
    * @Author: xixi
    * @Date: 2020/2/4
    */
//     void insTag(Tagcache tag);
    
    /**
    * @Description: 查询所有tag标签
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2020/2/4
    */
    List<TagDTO> get();
    
    /**
    * @Description: 过滤非法标签
    * @Param: tags
    * @return: 
    * @Author: xixi
    * @Date: 2020/2/4
    */
    String filterInvalid(String tags);
}
