package com.xixi.person.talk.Mapper;

import com.xixi.person.talk.Model.Tagcache;
import com.xixi.person.talk.Model.TagcacheExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagcacheMapper {
    int countByExample(TagcacheExample example);

    int deleteByExample(TagcacheExample example);

    int deleteByPrimaryKey(Short id);

    int insert(Tagcache record);

    int insertSelective(Tagcache record);

    List<Tagcache> selectByExample(TagcacheExample example);

    Tagcache selectByPrimaryKey(Short id);

    int updateByExampleSelective(@Param("record") Tagcache record, @Param("example") TagcacheExample example);

    int updateByExample(@Param("record") Tagcache record, @Param("example") TagcacheExample example);

    int updateByPrimaryKeySelective(Tagcache record);

    int updateByPrimaryKey(Tagcache record);
}