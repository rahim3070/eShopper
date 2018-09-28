package com.example.rahi.eshopper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AdminLoginActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mWebView = findViewById(R.id.webViewId);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //All Link are shown in apps not in browsers
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("https://mdabdurrahim.com/laravel/eshopper/admin-login");
    }

    // For back step by step
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
