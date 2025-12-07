// NewsView.java
package com.example.berita.presenter;

import com.example.berita.model.News;
import java.util.List;

public interface NewsView {
    void showLoading();
    void hideLoading();
    void showNews(List<News> newsList);
    void showError(String message);
    void showEmpty();
}