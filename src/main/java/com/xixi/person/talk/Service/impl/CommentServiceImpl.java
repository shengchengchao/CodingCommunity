package com.xixi.person.talk.Service.impl;


import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.xixi.person.talk.Service.CommentService;
import com.xixi.person.talk.Service.NotificationService;
import com.xixi.person.talk.dto.CommentDto;
import com.xixi.person.talk.enums.CommentTypeEnum;
import com.xixi.person.talk.enums.NotificationEnum;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.exception.QuestionException;
import com.xixi.person.talk.mapper.CommentMapper;
import com.xixi.person.talk.mapper.NotificationMapper;
import com.xixi.person.talk.mapper.QuestionMapper;
import com.xixi.person.talk.mapper.UserMapper;
import com.xixi.person.talk.model.Comment;
import com.xixi.person.talk.model.Notification;
import com.xixi.person.talk.model.Question;
import com.xixi.person.talk.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private  RedisTemplate redisTemplate;
    @Resource
    private NotificationService notificationServiceImpl;



    /**
     * 根据类型添加评论
     * @param comment
     * @param user
     */
    @Override
    public void insertComment(Comment comment, User user) {
        if(comment.getParentId() == null || comment.getParentId().equals("")){
            throw  new QuestionException(QuestionErrorCodeEnum.QUESTION_NOT_SELECT);
        }
        //评论类型找不到
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new QuestionException(QuestionErrorCodeEnum.TYPE_NOT_SELECT);
        }
        if(comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            //回复评论 先查询评论是否存在
            Comment parentComment = null;
            parentComment= new LambdaQueryChainWrapper<Comment>(commentMapper).eq(Comment::getParentId, comment.getParentId()).one();
            if(parentComment !=null && ! parentComment.equals("")){
                //存在评论
                comment.setCommentCount(0);
                commentMapper.insert(comment);
                Comment comment1=new Comment();
                //增加评论数 采用事务的方式
                comment1.setCommentCount(1);
                comment1.setId(comment.getParentId());
                commentMapper.updatecommentConunt(comment1);
                createNotification(user,parentComment.getParentId(),parentComment.getContent(),parentComment.getCommentator(), NotificationEnum.REPLY_COMMENT);
            }else {
                throw  new QuestionException(QuestionErrorCodeEnum.TYPE_NOT_SELECT);
            }
        }else {
            //回复问题
            Question question = null;
            new LambdaQueryChainWrapper<Question>(questionMapper).eq(Question::getId, comment.getParentId()).one();
            if(question != null && !question.equals("")){
                commentMapper.insert(comment);
                question.setCommentCount(1);
                //添加问题的评论数
                questionMapper.updatecommentConunt(question);
                createNotification(user,question.getId(),question.getTitle(),question.getCreatorId(),NotificationEnum.REPLY_QUESTION);
            }else {
                throw  new QuestionException(QuestionErrorCodeEnum.QUESTION_NOT_FOUND);
            }
        }



    }
    /**
     * @Description: 创建通知消息
     * @Author: shengchengchao
     * @Date: 2020/5/2
     * @param:
     * @return:
     **/
    private void createNotification(User user, Long outerid, String title, Long receiverId, NotificationEnum type) {
        //自己评论自己就不通知
        if(receiverId.equals(user.getAccountId())){
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
        //redis 记录通知数
        Integer unreadCount = (Integer) redisTemplate.opsForValue().get("unreadCount");
        if (unreadCount == null){
            unreadCount = notificationServiceImpl.unreadCount(record.getReceiver());
            redisTemplate.opsForValue().set("unreadCount",unreadCount);
        }
        redisTemplate.opsForValue().set("unreadCount",unreadCount+1);
    }




    /**
     * @Description:查询出问题下的所有评论 评论没有进行任何的筛选 为了显示出评论人信息 需要进行封装
     * @Author: shengchengchao
     * @Date: 2020/5/2
     * @param: id 问题的id
     * @return:
     **/
    @Override
    public List<CommentDto> selCommentList(long id, CommentTypeEnum type) {
        List<Comment> list = new LambdaQueryChainWrapper<Comment>(commentMapper).eq(Comment::getParentId, id)
                .eq(Comment::getType, type.getType()).orderByDesc(Comment::getGmtCreate).list();

        if(list==null || list.size()==0){
            return null;
        }
        List<CommentDto> commentList=new ArrayList<>();
        list.forEach(each-> {
            CommentDto commentDto =new CommentDto();
            BeanUtils.copyProperties(each,commentDto);
            User user = new LambdaQueryChainWrapper<User>(userMapper).eq(User::getAccountId, commentDto.getCommentator()).one();
            commentDto.setUser(user);
            commentList.add(commentDto);
        });
        return commentList;

    }
}
