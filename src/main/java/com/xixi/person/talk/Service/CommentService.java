package com.xixi.person.talk.Service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xixi.person.talk.dto.CommentDto;
import com.xixi.person.talk.enums.CommentTypeEnum;
import com.xixi.person.talk.model.Comment;
import com.xixi.person.talk.model.User;

import java.util.List;

public interface CommentService extends IService<Comment> {

    /**
     * @Description: 添加评论
     * @Author: shengchengchao
     * @Date: 2020/4/30
     * @param: comment  user
     * @return:
     **/
    void insertComment(Comment comment, User user);

    List<CommentDto> selCommentList(long id, CommentTypeEnum comment);
}
