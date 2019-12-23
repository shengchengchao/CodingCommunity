package com.xixi.person.talk.Service.impl;

import com.xixi.person.talk.Service.CommentService;
import com.xixi.person.talk.mapper.CommentMapper;
import com.xixi.person.talk.model.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/23 16:30
 * @Description:
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Resource
    private CommentMapper commentMapper;

    @Override
    public void insertComment(Comment comment) {
        commentMapper.insert(comment);
    }
}
