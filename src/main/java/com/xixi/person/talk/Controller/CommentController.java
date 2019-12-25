package com.xixi.person.talk.Controller;


import com.xixi.person.talk.Service.CommentService;
import com.xixi.person.talk.dto.ResultDto;
import com.xixi.person.talk.exception.QuestionErrorCodeEnum;
import com.xixi.person.talk.model.Comment;
import com.xixi.person.talk.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/23 16:22
 * @Description:
 */
@Controller
public class CommentController {
    @Resource
    private CommentService commentServiceInpl;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object addComment(@RequestBody Comment comment, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");

        if(user == null){
            //不返回index 页面  用了responsebody 返回不了静态页面
            return ResultDto.errorof(QuestionErrorCodeEnum.USER_NOT_FOUND);
        }
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());

        commentServiceInpl.insertComment(comment);
        return ResultDto.okof();
    }


}
