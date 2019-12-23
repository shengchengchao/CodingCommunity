package com.xixi.person.talk.Controller;


import com.xixi.person.talk.Service.CommentService;
import com.xixi.person.talk.model.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
    public Object addComment(@RequestBody Comment comment){
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);

        commentServiceInpl.insertComment(comment);
        return null;
    }


}
