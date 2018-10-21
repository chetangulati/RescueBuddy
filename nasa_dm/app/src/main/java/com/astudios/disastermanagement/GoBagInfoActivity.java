package com.astudios.disastermanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GoBagInfoActivity extends AppCompatActivity {

    private android.webkit.WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_bag_info);

        this.mWebView = (android.webkit.WebView) findViewById(R.id.webView);

        mWebView = (WebView) findViewById(R.id.webView);

        //first set webviewclient
        mWebView.setWebViewClient(new WebViewClient());

        //get the websettings from webview
        WebSettings webSettings = mWebView.getSettings();

        //now enable the javascript in the websettings
        webSettings.setJavaScriptEnabled(true);

        //now set the chrome client
        mWebView.setWebChromeClient(new WebChromeClient());

        //load url
        //mWebView.loadUrl("https://www.google.com/");

        mWebView.loadUrl("file:///android_asset/data.html");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
