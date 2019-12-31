package com.xixi.person.talk.Controller;

import com.xixi.person.talk.dto.FileDto;


import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/30 18:42
 * @Description:
 */
@Controller
public class fileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach
                                    ) {

        FileDto fileDTO = new FileDto();
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String rootPath = "F:/talk/src/main/resources/static/images";

            /**
             * 文件路径不存在则需要创建文件路径
             */
            File filePath = new File(rootPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            // 最终文件名
            File realFile = new File(rootPath + File.separator + attach.getOriginalFilename());

            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);
            // 下面response返回的json格式是editor.md所限制的，规范输出就OK
            fileDTO.setSuccess(1);

            fileDTO.setUrl("/images/"+attach.getOriginalFilename());
            fileDTO.setMessage("上传成功");
        } catch (Exception e) {
            fileDTO.setSuccess(0);
        }
        return fileDTO;
    }

}
