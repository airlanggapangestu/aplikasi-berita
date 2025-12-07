package com.example.berita.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.berita.R;
import com.example.berita.adapter.NewsAdapter;
import com.example.berita.model.News;
import com.example.berita.presenter.NewsPresenter;
import com.example.berita.presenter.NewsPresenterImpl;
import com.example.berita.presenter.NewsView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements NewsView {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ProgressBar progressBar;
    private NewsPresenter presenter;
    private List<News> newsList;

    public HomeFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NewsPresenterImpl(this);
        newsList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupRecyclerView();

        // Load berita
        presenter.getLatestNews("id", "id");

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerBerita);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), newsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                if (recyclerView != null) {
                    recyclerView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                if (recyclerView != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void showNews(List<News> newsList) {
        this.newsList.clear();
        this.newsList.addAll(newsList);

        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void showEmpty() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Tidak ada berita", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}