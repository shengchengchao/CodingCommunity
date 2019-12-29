package com.xixi.person.talk.Service;

import com.xixi.person.talk.Enum.CommentTypeEnum;
import com.xixi.person.talk.Enum.NotificationEnum;
import com.xixi.person.talk.dto.CommentDto;
import com.xixi.person.talk.model.Comment;
import com.xixi.person.talk.model.User;

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
    void insertComment(Comment comment, User user);

    /**
    * @Description:  获得所有的评论  评论包含 二级评论与一级评论
    * @Param: 
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/28
    */
    List<CommentDto> selCommentList(Long id,CommentTypeEnum type);

    void createNotification(User user,Long outerid,String title,Long receiverId,NotificationEnum type);
}
