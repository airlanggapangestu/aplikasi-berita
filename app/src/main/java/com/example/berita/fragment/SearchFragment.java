package com.example.berita.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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

public class SearchFragment extends Fragment implements NewsView {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private SearchView searchView;
    private ProgressBar progressBar;
    private NewsPresenter presenter;
    private List<News> newsList;

    public SearchFragment() {}

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

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initViews(view);
        setupRecyclerView();
        initSearch();

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerSearch);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBarSearch);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), newsList);
        recyclerView.setAdapter(adapter);
    }

    private void initSearch() {
        if (searchView != null) {
            searchView.clearFocus();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (!TextUtils.isEmpty(query.trim())) {
                        doSearch(query.trim());
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (TextUtils.isEmpty(newText.trim())) {
                        newsList.clear();
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void doSearch(String keyword) {
        presenter.searchNews(keyword, "id", "id");
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
                Toast.makeText(getContext(), "Tidak ada hasil pencarian", Toast.LENGTH_SHORT).show();
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