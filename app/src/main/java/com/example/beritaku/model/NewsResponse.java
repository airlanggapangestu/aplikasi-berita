package com.example.beritaku.model;

import com.example.berita.model.News;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NewsResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<News> articles;

    // Constructor
    public NewsResponse() {
        this.status = "";
        this.totalResults = 0;
    }

    // Getters
    public String getStatus() {
        return status != null ? status : "";
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<News> getArticles() {
        return articles;
    }

    // Setters
    public void setStatus(String status) { this.status = status; }
    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }
    public void setArticles(List<News> articles) { this.articles = articles; }
}