package com.xixi.pereson.talk.dto;

import org.springframework.stereotype.Component;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/15 21:16
 * @Description: 从github中获取的用户信息
 */

public class GithubUserdto {
    private String name;
    private Long id;
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "GithubUserdto{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}
