package com.xixi.person.talk.Service.impl;

import com.xixi.person.talk.Enum.CommentTypeEnum;
import com.xixi.person.talk.Service.CommentService;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.exception.QuestionException;
import com.xixi.person.talk.mapper.CommentMapper;
import com.xixi.person.talk.mapper.QuestionMapper;
import com.xixi.person.talk.mapper.QuestionextraMapper;
import com.xixi.person.talk.model.Comment;
import com.xixi.person.talk.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionextraMapper questionextraMapper;


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
}
