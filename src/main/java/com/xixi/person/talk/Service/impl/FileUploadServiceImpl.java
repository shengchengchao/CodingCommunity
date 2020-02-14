package com.xixi.person.talk.Service.impl;

import com.xixi.person.talk.Service.FileUploadService;
import com.xixi.person.talk.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: xixi-98
 * @Date: 2020/2/5 13:56
 * @Description:
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${ftpclient.host}")
    private String host;
    @Value("${ftpclient.port}")
    private int port;
    @Value("${ftpclient.username}")
    private String username;
    @Value("${ftpclient.password}")
    private String password;
    @Value("${ftpclient.basePath}")
    private String basePath;
    @Value("${ftpclient.filepath}")
    private String filePath;
    @Override
    public boolean upload(MultipartFile imgFile,String fileName) throws IOException {

        boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, fileName, imgFile.getInputStream());
        return result;
    }
}
