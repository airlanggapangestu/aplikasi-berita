// NewsPresenterImpl.java
package com.example.berita.presenter;

import android.util.Log;

import com.example.berita.model.News;
import com.example.berita.model.NewsResponse;
import com.example.berita.network.ApiClient;
import com.example.berita.network.ApiEndpoint;
import com.example.berita.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenterImpl implements NewsPresenter {

    private NewsView newsView;
    private ApiEndpoint apiEndpoint;

    public NewsPresenterImpl(NewsView newsView) {
        this.newsView = newsView;
        this.apiEndpoint = ApiClient.getClient().create(ApiEndpoint.class);
    }

    @Override
    public void getLatestNews(String country, String language) {
        newsView.showLoading();

        Call<NewsResponse> call = apiEndpoint.getBerita(
                Constants.API_KEY,
                country,
                language
        );

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                newsView.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    NewsResponse newsResponse = response.body();
                    List<News> newsList = newsResponse.getResults();

                    if (newsList != null && !newsList.isEmpty()) {
                        newsView.showNews(newsList);
                    } else {
                        newsView.showEmpty();
                    }
                } else {
                    String errorMsg = "Error: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    newsView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsView.hideLoading();
                newsView.showError("Gagal terhubung: " + t.getMessage());
                Log.e("NewsPresenter", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void searchNews(String query, String country, String language) {
        newsView.showLoading();

        Call<NewsResponse> call = apiEndpoint.searchBerita(
                Constants.API_KEY,
                query,
                country,
                language
        );

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                newsView.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    NewsResponse newsResponse = response.body();
                    List<News> newsList = newsResponse.getResults();

                    if (newsList != null && !newsList.isEmpty()) {
                        newsView.showNews(newsList);
                    } else {
                        newsView.showEmpty();
                    }
                } else {
                    String errorMsg = "Error: " + response.code();
                    newsView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsView.hideLoading();
                newsView.showError("Gagal mencari: " + t.getMessage());
                Log.e("NewsPresenter", "Search Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        newsView = null;
    }

    @Override
    public void getNewsByCategory(String category, String country, String language) {
        newsView.showLoading();

        Call<NewsResponse> call = apiEndpoint.getNewsByCategory(
                Constants.API_KEY,
                category,
                country,
                language
        );

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                newsView.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    List<News> newsList = response.body().getResults();
                    if (newsList != null && !newsList.isEmpty()) {
                        newsView.showNews(newsList);
                    } else {
                        newsView.showEmpty();
                    }
                } else {
                    newsView.showError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsView.hideLoading();
                newsView.showError("Gagal: " + t.getMessage());
            }
        });
    }

    @Override
    public void getNewsByCategories(List<String> categories, String country, String language) {
        // Similar implementation for multiple categories
    }
}