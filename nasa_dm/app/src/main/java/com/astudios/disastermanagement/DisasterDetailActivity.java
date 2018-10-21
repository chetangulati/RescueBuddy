package com.astudios.disastermanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import essential.Essential;
import model.DisasterInfo;

public class DisasterDetailActivity extends AppCompatActivity {

    private android.widget.TextView disastertitle;
    private android.widget.ImageView disasterimg;
    private android.widget.TextView disasterdesc;
    private android.webkit.WebView mWebView;
    private android.widget.TextView disasterevac;
    private android.widget.TextView disastershelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_detail);

        this.disastershelter = (TextView) findViewById(R.id.disaster_shelter);
        this.disasterevac = (TextView) findViewById(R.id.disaster_evac);

        this.mWebView = (android.webkit.WebView) findViewById(R.id.disaster_video);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());

        this.disasterdesc = (TextView) findViewById(R.id.disaster_desc);
        this.disasterimg = (ImageView) findViewById(R.id.disaster_img);
        this.disastertitle = (TextView) findViewById(R.id.disaster_title);


        setUpData(DisasterInfoActivity.disasterData.get(getIntent().getIntExtra("index", -1)));
    }

    private void setUpData(DisasterInfo data) {
        disastertitle.setText(data.getDisasterName());
        disastershelter.setText(data.getShelterSteps());
        disasterdesc.setText(data.getDisasterDesc());
        //image use picasso
        Picasso.get().load(Essential.BASE_URL + data.getDisasterImage()).into(disasterimg);
        mWebView.loadUrl(data.getVideoLink());
        disasterevac.setText(data.getEvacSteps());

    }

}
