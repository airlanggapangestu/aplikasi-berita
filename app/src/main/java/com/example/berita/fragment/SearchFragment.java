package com.example.berita.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.berita.R;
import com.example.berita.adapter.NewsAdapter;
import com.example.berita.model.NewsResponse;
import com.example.berita.network.ApiClient;
import com.example.berita.network.ApiEndpoint;
import com.example.berita.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private SearchView searchView;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerSearch);
        searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initSearch();

        return view;
    }

    private void initSearch() {
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    doSearch(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void doSearch(String keyword) {
        ApiEndpoint api = ApiClient.getClient().create(ApiEndpoint.class);
        Call<NewsResponse> call = api.searchBerita(Constants.API_KEY, keyword, "id", "id");

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if (!response.isSuccessful()) {
                    showToast("Response error: " + response.code());
                    return;
                }
                NewsResponse body = response.body();
                if (body == null || body.getResults() == null || body.getResults().isEmpty()) {
                    showToast("Tidak ada hasil untuk \"" + keyword + "\"");
                    return;
                }

                adapter = new NewsAdapter(getContext(), body.getResults());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                showToast("Gagal: " + t.getMessage());
            }
        });
    }

    private void showToast(String msg) {
        if (getContext() != null) Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
