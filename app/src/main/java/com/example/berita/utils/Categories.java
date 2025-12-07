// Categories.java
package com.example.berita.utils;
import com.example.berita.R;


import java.util.Arrays;
import java.util.List;

public class Categories {

    public static final String GENERAL = "general";
    public static final String TECHNOLOGY = "technology";
    public static final String BUSINESS = "business";
    public static final String SPORTS = "sports";
    public static final String ENTERTAINMENT = "entertainment";
    public static final String HEALTH = "health";
    public static final String SCIENCE = "science";
    public static final String POLITICS = "politics";

    public static List<String> getAllCategories() {
        return Arrays.asList(
                GENERAL, TECHNOLOGY, BUSINESS, SPORTS,
                ENTERTAINMENT, HEALTH, SCIENCE, POLITICS
        );
    }

    public static String getDisplayName(String categoryId) {
        switch (categoryId) {
            case GENERAL: return "Umum";
            case TECHNOLOGY: return "Teknologi";
            case BUSINESS: return "Bisnis";
            case SPORTS: return "Olahraga";
            case ENTERTAINMENT: return "Hiburan";
            case HEALTH: return "Kesehatan";
            case SCIENCE: return "Sains";
            case POLITICS: return "Politik";
            default: return "Umum";
        }
    }

    public static int getIconResource(String categoryId) {
        switch (categoryId) {
            case TECHNOLOGY: return R.drawable.ic_technology;
            case BUSINESS: return R.drawable.ic_business;
            case SPORTS: return R.drawable.ic_sports;
            case ENTERTAINMENT: return R.drawable.ic_entertainment;
            case HEALTH: return R.drawable.ic_health;
            case SCIENCE: return R.drawable.ic_science;
            case POLITICS: return R.drawable.ic_politics;
            default: return R.drawable.ic_general;
        }
    }
}