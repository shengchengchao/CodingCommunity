package com.xixi.person.talk.Service.impl;

import com.xixi.person.talk.Enum.CommentTypeEnum;
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

    @Override
    @Transactional
    public void insertComment(Comment comment) {
        //找不到问题
        if(comment.getParentId() == null || comment.getParentId().equals("")){
            throw  new QuestionException(QuestionErrorCodeEnum.QUESTION_NOT_SELECT);
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new QuestionException(QuestionErrorCodeEnum.TYPE_NOT_SELECT);
        }
        if(comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            //回复评论 先查询评论是否存在
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(parentComment !=null && ! parentComment.equals("")){
                commentMapper.insert(comment);
                Comment comment1=new Comment();
                comment1.setCommentCount(1);
                comment1.setId(comment.getParentId());
                commentextraMapper.updatecommentConunt(comment1);
            }else {
                throw  new QuestionException(QuestionErrorCodeEnum.TYPE_NOT_SELECT);
            }
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question != null && !question.equals("")){
                commentMapper.insert(comment);
                question.setCommentCount(1);
                questionextraMapper.updatecommentConunt(question);
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

        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        List<CommentDto> commentList=new ArrayList<>();
        if(comments.size()==0){
            return commentList;
        }
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
}
