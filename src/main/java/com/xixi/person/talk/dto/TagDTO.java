package com.xixi.person.talk.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/27 16:35
 * @Description:
 */
public class TagDTO implements Serializable {
    private String categoryName;
    private List<String> tags;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "categoryName='" + categoryName + '\'' +
                ", tags=" + tags +
                '}';
    }
}
