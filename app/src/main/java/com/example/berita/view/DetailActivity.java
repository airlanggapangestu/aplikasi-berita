package com.example.berita.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.berita.R;

public class DetailActivity extends AppCompatActivity {

    ImageView imgDetail, btnBack;
    TextView tvTitle, tvContent;
    Button btnBacaWeb;
    String urlBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bind UI
        imgDetail = findViewById(R.id.imgDetail);
        tvTitle = findViewById(R.id.tvTitleDetail);
        tvContent = findViewById(R.id.tvContentDetail);
        btnBacaWeb = findViewById(R.id.btnBacaWeb);
        btnBack = findViewById(R.id.btnBack);

        // Tombol back
        btnBack.setOnClickListener(v -> onBackPressed());

        // Ambil data dari intent
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String description = getIntent().getStringExtra("description"); // deskripsi singkat
        urlBerita = getIntent().getStringExtra("url"); // opsional, untuk WebView

        // Set data ke UI
        tvTitle.setText(title);
        tvContent.setText(description);

        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.placeholder)
                .into(imgDetail);

        // Tombol "Baca Selengkapnya"
        btnBacaWeb.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, WebViewActivity.class);

            // Jika URL tidak ada, gunakan default
            if (urlBerita == null || urlBerita.isEmpty()) {
                urlBerita = "https://news.google.com/";
            }

            intent.putExtra("url", urlBerita);
            startActivity(intent);
        });
    }
}
