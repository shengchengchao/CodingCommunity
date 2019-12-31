package com.xixi.person.talk.Service.impl;

import com.xixi.person.talk.Enum.CommentTypeEnum;
import com.xixi.person.talk.Enum.NotificationEnum;
import com.xixi.person.talk.Service.CommentService;
import com.xixi.person.talk.dto.CommentDto;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.exception.QuestionException;
import com.xixi.person.talk.mapper.*;
import com.xixi.person.talk.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/23 16:30
 * @Description:
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionextraMapper questionextraMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentextraMapper commentextraMapper;
    @Resource
    private NotificationMapper notificationMapper;

    @Override
    @Transactional
    public void insertComment(Comment comment, User user) {
        //找不到问题
        if(comment.getParentId() == null || comment.getParentId().equals("")){
            throw  new QuestionException(QuestionErrorCodeEnum.QUESTION_NOT_SELECT);
        }
        //评论类型找不到
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new QuestionException(QuestionErrorCodeEnum.TYPE_NOT_SELECT);
        }
        if(comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            //回复评论 先查询评论是否存在
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(parentComment !=null && ! parentComment.equals("")){
                comment.setCommentCount(0);
                commentMapper.insert(comment);
                Comment comment1=new Comment();
                comment1.setCommentCount(1);
                comment1.setId(comment.getParentId());
                commentextraMapper.updatecommentConunt(comment1);
                createNotification(user,parentComment.getParentId(),parentComment.getContent(),parentComment.getCommentator(),NotificationEnum.REPLY_COMMENT);
            }else {
                throw  new QuestionException(QuestionErrorCodeEnum.TYPE_NOT_SELECT);
            }
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question != null && !question.equals("")){
                commentMapper.insert(comment);
                question.setCommentCount(1);
                //添加问题的评论数
                questionextraMapper.updatecommentConunt(question);
                createNotification(user,question.getId(),question.getTitle(),question.getCreatorId(),NotificationEnum.REPLY_QUESTION);
            }else {
                throw  new QuestionException(QuestionErrorCodeEnum.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
    * @Description: 查询出问题下的所有评论   评论没有进行任何的筛选 为了显示出评论人信息 需要进行封装
    * @Param: id 问题的id
    * @return: 
    * @Author: xixi
    * @Date: 2019/12/26
    */
    @Override
    public List<CommentDto> selCommentList(Long id,CommentTypeEnum type) {
        //按照时间倒序的方式 返回问题下的评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        List<CommentDto> commentList=new ArrayList<>();
        if(comments.size()==0){
            return commentList;
        }
        //需要返回该评论的用户信息 可以使用 java8的stream 来简化
        for (Comment comment : comments) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(commentDto.getCommentator());
            List<User> users = userMapper.selectByExample(userExample);
            commentDto.setUser(users.get(0));
            commentList.add(commentDto);
        }

        return commentList;
    }

    @Override
    public void createNotification(User user,Long outerid,String title,Long receiverId,NotificationEnum type) {
        //自己评论自己就不通知
        if(receiverId==user.getAccountId()){
            return ;
        }
        Notification record = new Notification();
        record.setGmtCreate(System.currentTimeMillis());
        record.setNotifier(user.getAccountId());
        record.setNotifierName(user.getName());
        record.setOuterTitle(title);
        record.setReceiver(receiverId);
        record.setStatus(NotificationEnum.UEREAD.getType());
        record.setType(type.getType());
        record.setOuterid(outerid);
        notificationMapper.insert(record);

    }
}
