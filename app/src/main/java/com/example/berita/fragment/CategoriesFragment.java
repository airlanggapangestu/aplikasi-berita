package com.example.berita.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.berita.R;
import com.example.berita.adapter.CategoryAdapter;
import com.example.berita.model.Category;
import com.example.berita.utils.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener {

    private RecyclerView recyclerViewCategories;
    private CategoryAdapter categoryAdapter;
    private List<Category> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerViewCategories = view.findViewById(R.id.recyclerCategories);
        initCategories();
        setupRecyclerView();

        return view;
    }

    private void initCategories() {
        categories = new ArrayList<>();

        // Add all categories
        categories.add(new Category(
                Categories.GENERAL,
                Categories.GENERAL,
                "Umum",
                Categories.getIconResource(Categories.GENERAL)
        ));

        categories.add(new Category(
                Categories.TECHNOLOGY,
                Categories.TECHNOLOGY,
                "Teknologi",
                Categories.getIconResource(Categories.TECHNOLOGY)
        ));

        categories.add(new Category(
                Categories.BUSINESS,
                Categories.BUSINESS,
                "Bisnis",
                Categories.getIconResource(Categories.BUSINESS)
        ));

        categories.add(new Category(
                Categories.SPORTS,
                Categories.SPORTS,
                "Olahraga",
                Categories.getIconResource(Categories.SPORTS)
        ));

        categories.add(new Category(
                Categories.ENTERTAINMENT,
                Categories.ENTERTAINMENT,
                "Hiburan",
                Categories.getIconResource(Categories.ENTERTAINMENT)
        ));

        categories.add(new Category(
                Categories.HEALTH,
                Categories.HEALTH,
                "Kesehatan",
                Categories.getIconResource(Categories.HEALTH)
        ));

        categories.add(new Category(
                Categories.SCIENCE,
                Categories.SCIENCE,
                "Sains",
                Categories.getIconResource(Categories.SCIENCE)
        ));

        categories.add(new Category(
                Categories.POLITICS,
                Categories.POLITICS,
                "Politik",
                Categories.getIconResource(Categories.POLITICS)
        ));

        // Tambah lebih banyak kategori jika mau
        // categories.add(new Category("world", "world", "Dunia", R.drawable.ic_world));
        // categories.add(new Category("environment", "environment", "Lingkungan", R.drawable.ic_environment));
    }

    private void setupRecyclerView() {
        // GANTI: LinearLayoutManager VERTIKAL (bukan horizontal)
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,  // <- INI
                false
        );

        categoryAdapter = new CategoryAdapter(requireContext(), categories, this);
        recyclerViewCategories.setLayoutManager(layoutManager);
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    @Override
    public void onCategoryClick(Category category) {
        // Navigate to CategoryNewsFragment
        CategoryNewsFragment fragment = CategoryNewsFragment.newInstance(category.getId());

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerFragment, fragment)
                .addToBackStack("category_news")
                .commit();
    }
}