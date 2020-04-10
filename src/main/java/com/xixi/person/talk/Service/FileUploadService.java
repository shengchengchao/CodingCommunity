package com.xixi.person.talk.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: xixi-98
 * @Date: 2020/2/5 13:55
 * @Description:
 */
public interface FileUploadService {
    public boolean upload(MultipartFile imgFile,String fileName) throws IOException;
}
