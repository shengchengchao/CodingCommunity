package com.xixi.person.talk.controller;

import com.xixi.person.talk.Service.FileUploadService;
import com.xixi.person.talk.dto.FileDto;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: xixi-98
 * @Date: 2019/12/30 18:42
 * @Description:
 */
@Controller
public class fileController {
    @Resource
    private FileUploadService fileUploadServiceImpl;
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach
                                    ) {

        FileDto fileDTO = new FileDto();
        try {
            String fileName= UUID.randomUUID()+attach.getOriginalFilename().substring(attach.getOriginalFilename().lastIndexOf("."));
            boolean result = fileUploadServiceImpl.upload(attach,fileName);
            if(result){
                // 下面response返回的json格式是editor.md所限制的，规范输出就OK
                fileDTO.setSuccess(1);
                fileDTO.setUrl("http://121.36.26.52/"+fileName);
                fileDTO.setMessage("上传成功");
            }
        } catch (Exception e) {
            fileDTO.setSuccess(0);
        }
        return fileDTO;
    }

}
