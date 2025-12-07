package com.example.berita.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.berita.R;
import com.example.berita.adapter.NewsAdapter;
import com.example.berita.model.NewsResponse;
import com.example.berita.network.ApiClient;
import com.example.berita.network.ApiEndpoint;
import com.example.berita.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // layout fragment mengikuti tema
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerBerita);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // load berita dari API
        loadBerita();

        return view;
    }

    private void loadBerita() {
        ApiEndpoint api = ApiClient.getClient().create(ApiEndpoint.class);
        Call<NewsResponse> call = api.getBerita(Constants.API_KEY, "id", "id");

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if (!response.isSuccessful()) {
                    showToast("Response error: " + response.code());
                    return;
                }

                NewsResponse body = response.body();
                if (body == null || body.getResults() == null) {
                    showToast("Tidak ada data");
                    return;
                }

                adapter = new NewsAdapter(getContext(), body.getResults());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                showToast("Gagal: " + t.getMessage());
                Log.e("API_ERROR", t.getMessage() != null ? t.getMessage() : "null");
            }
        });
    }

    private void showToast(String msg) {
        if (getContext() != null) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
