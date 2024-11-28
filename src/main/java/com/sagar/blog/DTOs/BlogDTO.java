package com.sagar.blog.DTOs;

import java.util.List;

public class BlogDTO {
    public Long id;
    public String title;
    public String content;
    public Long categoryId;
    public List<Long> tagIds;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }

    public Long getCategoryId(){
        return this.categoryId;
    }

    public void setTagIds(List<Long> tagIds){
        this.tagIds = tagIds;
    }

    public List<Long> getTagIds(){
        return tagIds;
    }
}
