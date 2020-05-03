package com.xixi.person.talk.Service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xixi.person.talk.dto.TagDTO;
import com.xixi.person.talk.model.Tagcache;

import java.util.List;

public interface TagcacheService extends IService<Tagcache> {


    List<TagDTO> get();

    String filterInvalid(String tag);
}
