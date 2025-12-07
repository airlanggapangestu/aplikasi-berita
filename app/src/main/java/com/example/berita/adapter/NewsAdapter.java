package com.example.berita.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.berita.view.DetailActivity;
import com.example.berita.R;
import com.example.berita.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<News> newsList;

    public NewsAdapter(Context context, List<News> list) {
        this.context = context;
        this.newsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News n = newsList.get(position);

        holder.tvTitle.setText(n.getTitle() != null ? n.getTitle() : "—");
        holder.tvDescription.setText(n.getDescription() != null ? n.getDescription() : "");

        String imageUrl = n.getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(holder.imgNews);
        } else {
            holder.imgNews.setImageResource(R.drawable.placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", n.getTitle());
            intent.putExtra("image", n.getImageUrl());
            // kirim description sebagai content fallback
            intent.putExtra("description", n.getDescription());
            intent.putExtra("url", n.getLink());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return newsList == null ? 0 : newsList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView tvTitle, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
