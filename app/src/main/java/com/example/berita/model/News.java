package com.example.berita.model;

import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("title")
    private String title;

    @SerializedName("link")
    private String link;

    @SerializedName("description")
    private String description;

    // API biasanya pakai "image_url"
    @SerializedName("image_url")
    private String image_url;

    // kadang ada field "content"
    @SerializedName("content")
    private String content;

    // getters
    public String getTitle() { return title; }
    public String getLink() { return link; }
    public String getDescription() { return description; }
    public String getImageUrl() { return image_url; }
    public String getContent() { return content; }
}
