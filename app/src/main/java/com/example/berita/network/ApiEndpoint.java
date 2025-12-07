package com.example.berita.network;

import com.example.berita.model.NewsResponse;

import java.util.List;  // ← TAMBAHKAN IMPORT INI

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

    // Get news by category - BARU
    @GET("news")
    Call<NewsResponse> getNewsByCategory(
            @Query("apikey") String apiKey,
            @Query("category") String category,  // ← String biasa
            @Query("country") String country,
            @Query("language") String language
    );

    // Get news with multiple categories - OPTIONAL
    // Hapus dulu jika tidak diperlukan
    /*
    @GET("news")
    Call<NewsResponse> getNewsByCategories(
            @Query("apikey") String apiKey,
            @Query("category") List<String> categories,
            @Query("country") String country,
            @Query("language") String language
    );
    */
}