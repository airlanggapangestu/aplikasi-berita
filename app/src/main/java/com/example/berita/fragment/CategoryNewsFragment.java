// CategoryNewsFragment.java
package com.example.berita.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class CategoryNewsFragment extends Fragment implements NewsView {

    private static final String ARG_CATEGORY = "category";

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    private NewsPresenter presenter;
    private List<News> newsList;
    private String currentCategory;

    public static CategoryNewsFragment newInstance(String category) {
        CategoryNewsFragment fragment = new CategoryNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentCategory = getArguments().getString(ARG_CATEGORY, "general");
        }
        presenter = new NewsPresenterImpl(this);
        newsList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_news, container, false);

        initViews(view);
        setupRecyclerView();
        loadCategoryNews();

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerCategoryNews);
        progressBar = view.findViewById(R.id.progressBarCategory);
        tvEmpty = view.findViewById(R.id.tvEmptyCategory);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), newsList);
        recyclerView.setAdapter(adapter);
    }

    private void loadCategoryNews() {
        presenter.getNewsByCategory(currentCategory, "id", "id");
    }

    // Implement NewsView methods
    @Override
    public void showLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.GONE);
            });
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            });
        }
    }

    @Override
    public void showNews(List<News> newsList) {
        this.newsList.clear();
        this.newsList.addAll(newsList);

        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                adapter.notifyDataSetChanged();

                if (newsList.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("Tidak ada berita untuk kategori ini");
                } else {
                    tvEmpty.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText(message);
            });
        }
    }

    @Override
    public void showEmpty() {
        showError("Tidak ada berita untuk kategori ini");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}