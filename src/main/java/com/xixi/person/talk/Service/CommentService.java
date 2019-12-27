package com.xixi.person.talk.Service;

import com.xixi.person.talk.Enum.CommentTypeEnum;
import com.xixi.person.talk.dto.CommentDto;
import com.xixi.person.talk.model.Comment;

import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/23 16:29
 * @Description:
 */
public interface CommentService {
    
    /**
    * @Description: 添加评论
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/23
    */
    void insertComment(Comment comment);

    List<CommentDto> selCommentList(Long id,CommentTypeEnum type);
}
