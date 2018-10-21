package com.astudios.disastermanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import essential.CustomViewPager;
import essential.Essential;
import essential.SectionsPagerAdapter;

public class MeetUpActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MAX_FRAGS = 4;
    public int FRAG_COUNT = 0;
    CustomViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    private android.widget.Button meetPrevBt;
    private android.widget.Button meetNextBt;
    int counter = 0;
    Essential essential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_up);

        essential = new Essential(this);
        this.meetNextBt = (Button) findViewById(R.id.meetNextBt);
        meetNextBt.setOnClickListener(this);
        this.meetPrevBt = (Button) findViewById(R.id.meetPrevBt);
        meetPrevBt.setOnClickListener(this);


        setUpViewPager();

    }

    private void setUpViewPager() {
        //setting up the viewpager with different tabs
        viewPager = (CustomViewPager) findViewById(R.id.mainTabPager);
        viewPager.disableScroll(true);

        //setting up the adapter
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //setting in the adapter to viewpager
        viewPager.setAdapter(sectionsPagerAdapter);

        viewPager.setCurrentItem(FRAG_COUNT, true);
    }

    @Override
    public void onClick(View view) {
       // essential.show("clicked", Essential.SUCCESS);
        switch (view.getId()) {
            case R.id.meetNextBt:
                if (FRAG_COUNT < MAX_FRAGS + 1)
                    viewPager.setCurrentItem(++FRAG_COUNT, true);
                else
                {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
                break;
            case R.id.meetPrevBt:
                if (FRAG_COUNT == 0) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
                viewPager.setCurrentItem(--FRAG_COUNT, true);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
