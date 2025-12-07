package com.example.berita.presenter;

import com.example.berita.model.News;
import java.util.List;

public interface NewsPresenter {
    // Untuk HomeFragment
    void getLatestNews(String country, String language);

    // Untuk SearchFragment
    void searchNews(String query, String country, String language);

    // Untuk membersihkan resource
    void onDestroy();
}