package com.example.berita.view;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.berita.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webView);

        // Ambil URL dari Intent
        String url = getIntent().getStringExtra("url");

        // Setting WebView
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        // Muat URL
        if (url != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        // Jika WebView masih bisa back → back dulu
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed(); // Keluar Activity
        }
    }
}
