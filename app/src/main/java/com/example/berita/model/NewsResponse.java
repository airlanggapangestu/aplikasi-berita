package com.example.berita.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NewsResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int totalResults;

    // NewsData.io mengembalikan array "results"
    @SerializedName("results")
    private List<News> results;

    public String getStatus() { return status; }
    public int getTotalResults() { return totalResults; }
    public List<News> getResults() { return results; }
}
