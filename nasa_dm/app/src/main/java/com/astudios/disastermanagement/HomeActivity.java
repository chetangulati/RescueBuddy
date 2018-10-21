package com.astudios.disastermanagement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

import essential.Essential;
import info.abdolahi.CircularMusicProgressBar;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private CircularMusicProgressBar seekBarProgress;
    private static MediaPlayer mediaplayer = null;
    private TextView progressTv;
    private android.widget.Button goBagBt;
    private android.widget.Button goBagInfoBt;
    private android.widget.Button meetupBt;
    private android.widget.Button disasterBt;
    private android.widget.Button newsBt;
    private android.widget.Button evacBt;
    private int progress = 0;
    private final int MAX_PROGRESS = 100;
    private Essential essential;
    private WifiManager wifi;
    private List<ScanResult> scanResults;
    private SharedPreferences sharedPreferences;
    public static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //connecting ids
        findIds();
        setUpEmergency();


    }

    private void setUpEmergency() {
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false) {
            essential.show("Wifi is Turning on...", Essential.INFO);
            wifi.setWifiEnabled(true);
        }

        //Mediaplayer Object with a sound
        if (mediaplayer == null) {
            mediaplayer = MediaPlayer.create(this, R.raw.sound1);
            mediaplayer.setLooping(true);
        }
    }

    BroadcastReceiver wifi_receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context c, Intent intent) {

            scanResults = wifi.getScanResults();
            int size = scanResults.size();
            unregisterReceiver(this);

            for (ScanResult s : scanResults) {
                //essential.show(s.SSID, Essential.SUCCESS);
                if (s.SSID.equals(Essential.WIFI_NAME) || s.BSSID.equals(Essential.WIFI_NAME)) {

                    mediaplayer.start();

                } else {
                    scanWifiNetworks();
                }

            }


        }
    };

    private void scanWifiNetworks() {

        registerReceiver(wifi_receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifi.startScan();


    }

    @Override
    protected void onResume() {
        super.onResume();

        //check spf for progress and update
        updateProgress();
    }

    private void updateProgress() {
        int pc = sharedPreferences.getInt(Essential.Percent_Key, -1);
        if (pc > -1) {
            seekBarProgress.setValue(pc);
            progressTv.setText(pc + "");
        } else {
            seekBarProgress.setValue(0);
            progressTv.setText(0 + "");
        }
    }

    private void findIds() {
        essential = new Essential(this);
        sharedPreferences = getSharedPreferences(Essential.PACKAGE_NAME, Context.MODE_PRIVATE);
        this.evacBt = (Button) findViewById(R.id.evacBt);
        evacBt.setOnClickListener(this);

        this.newsBt = (Button) findViewById(R.id.newsBt);
        newsBt.setOnClickListener(this);

        this.disasterBt = (Button) findViewById(R.id.disasterBt);
        disasterBt.setOnClickListener(this);

        this.meetupBt = (Button) findViewById(R.id.meetupBt);
        meetupBt.setOnClickListener(this);

        this.goBagInfoBt = (Button) findViewById(R.id.goBagInfoBt);
        goBagInfoBt.setOnClickListener(this);

        this.goBagBt = (Button) findViewById(R.id.goBagBt);
        goBagBt.setOnClickListener(this);

        this.progressTv = (TextView) findViewById(R.id.progressTv);
        progressTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                progressTv.setText("Rescue");
                essential.show("Rescue Started!", Essential.INFO);
                scanWifiNetworks();
                return false;
            }
        });

        this.seekBarProgress = (CircularMusicProgressBar) findViewById(R.id.seekBarProgress);
        //disable touch for seekBar
        disableSeekBarTouch();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.evacBt:
                startActivity(new Intent(this, EvacShelActivity.class));
                finish();
                break;
            case R.id.newsBt:
                startActivity(new Intent(this, NewsActivity.class));
                finish();
                break;
            case R.id.disasterBt:
                startActivity(new Intent(this, DisasterInfoActivity.class));
                finish();
                break;
            case R.id.meetupBt:
                startActivity(new Intent(this, MeetUpActivity.class));
                finish();
                break;
            case R.id.goBagInfoBt:
                startActivity(new Intent(this, GoBagInfoActivity.class));
                finish();
                break;
            case R.id.goBagBt:
                startActivity(new Intent(this, GoBagActivity.class));
                finish();
                break;
        }

    }

    private void disableSeekBarTouch() {
        seekBarProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }


}
