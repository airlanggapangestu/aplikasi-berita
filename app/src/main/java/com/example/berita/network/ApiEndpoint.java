package com.example.berita.network;

import com.example.berita.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpoint {

    // mengambil berita terbaru (country + language)
    @GET("news")
    Call<NewsResponse> getBerita(
            @Query("apikey") String apiKey,
            @Query("country") String country,
            @Query("language") String language
    );

    // search: q = query kata kunci
    @GET("news")
    Call<NewsResponse> searchBerita(
            @Query("apikey") String apiKey,
            @Query("q") String query,
            @Query("country") String country,
            @Query("language") String language
    );
}
