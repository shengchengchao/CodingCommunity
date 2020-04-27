package com.xixi.person.talk.dto;

import java.io.Serializable;

/**
 * @Author: xixi-98
 * @Date: 2019/12/30 18:43
 * @Description:
 */
public class FileDto implements Serializable {
    private int success;
    private String message;
    private String url;
    private String urls

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
