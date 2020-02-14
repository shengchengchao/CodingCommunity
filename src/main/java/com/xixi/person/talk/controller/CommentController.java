package com.xixi.person.talk.controller;


import com.xixi.person.talk.enums.CommentTypeEnum;
import com.xixi.person.talk.Service.CommentService;
import com.xixi.person.talk.dto.CommentDto;
import com.xixi.person.talk.dto.ResultDto;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.Model.Comment;
import com.xixi.person.talk.Model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: xixi-98
 * @Date: 2019/12/23 16:22
 * @Description:
 */
@Controller
public class CommentController {
    @Resource
    private CommentService commentServiceImpl;
    
    /**
    * @Description: 添加评论
    * @Param: comment request
    * @return: 
    * @Author: xixi
    * @Date: 2020/2/11
    */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object addComment(@RequestBody Comment comment, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            //不返回index 页面  用了responsebody 返回不了静态页面
            return ResultDto.errorof(QuestionErrorCodeEnum.USER_NOT_FOUND);
        }
        if (comment==null || StringUtils.isBlank(comment.getContent())){
            return ResultDto.errorof(QuestionErrorCodeEnum.COMMENT_IS_EMPTY);
        }
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        comment.setCommentator(user.getAccountId());
        //添加评论
        commentServiceImpl.insertComment(comment,user);
        return ResultDto.okof();
    }

    /**
    * @Description: 返回一个评论下的二级评论的列表
    * @Param: id 问题的id
    * @return:  该问题下的评论
    * @Author: xixi
    * @Date: 2019/12/28
    */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> addComment(@PathVariable(name="id")long id){
        List<CommentDto> commentDtos = commentServiceImpl.selCommentList(id, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDtos);
    }

}
