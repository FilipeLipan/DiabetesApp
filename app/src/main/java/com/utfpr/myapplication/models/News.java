package com.utfpr.myapplication.models;

public class News {
    private String image;
    private String link;
    private String title;

    public String getImage() {
        return image;
    }

    public News setImage(String image) {
        this.image = image;
        return this;
    }

    public String getLink() {
        return link;
    }

    public News setLink(String link) {
        this.link = link;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public News setTitle(String title) {
        this.title = title;
        return this;
    }
}
