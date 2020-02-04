package com.xixi.person.talk.model;

public class Tagcache {
    private Short id;

    private String cache;

    private String name;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache == null ? null : cache.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}