package com.xixi.person.talk.mapper;


import com.xixi.person.talk.Config.SuperMapper;
import com.xixi.person.talk.model.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xixi-98
 */

public interface CommentMapper extends SuperMapper<Comment> {


    void updatecommentConunt(Comment comment1);
}