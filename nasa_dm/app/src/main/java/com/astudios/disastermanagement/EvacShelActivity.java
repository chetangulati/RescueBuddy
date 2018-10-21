package com.astudios.disastermanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;

import essential.Essential;

public class EvacShelActivity extends AppCompatActivity {
    public Essential essential;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private android.support.design.widget.TextInputEditText profileNameEt;
    private android.support.design.widget.TextInputEditText profileAgeEt;
    private android.widget.RadioButton profilemaleId;
    private android.widget.RadioButton profilefemaleId;
    private android.widget.RadioButton profileotherId;
    private android.widget.RadioGroup profilegenderRg;
    private android.support.design.widget.TextInputEditText profileMobileEt;
    private android.support.design.widget.TextInputEditText profileHeightEt;
    private android.support.design.widget.TextInputEditText profileWeightEt;
    private android.widget.Button profileSaveBt;
    private android.widget.Button logoutBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evac_shel);

        findIds();

        setUpFields();

    }

    private void findIds() {
        this.logoutBt = (Button) findViewById(R.id.logoutBt);
        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().apply();
                startActivity(new Intent(EvacShelActivity.this, LoginActivity.class));
                finish();
            }
        });
        this.profileSaveBt = (Button) findViewById(R.id.profileSaveBt);
        profileSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        this.profileWeightEt = (TextInputEditText) findViewById(R.id.profileWeightEt);
        this.profileHeightEt = (TextInputEditText) findViewById(R.id.profileHeightEt);
        this.profileMobileEt = (TextInputEditText) findViewById(R.id.profileMobileEt);
        this.profilegenderRg = (RadioGroup) findViewById(R.id.profilegenderRg);
        this.profileotherId = (RadioButton) findViewById(R.id.profileotherId);
        this.profilefemaleId = (RadioButton) findViewById(R.id.profilefemaleId);
        this.profilemaleId = (RadioButton) findViewById(R.id.profilemaleId);
        this.profileAgeEt = (TextInputEditText) findViewById(R.id.profileAgeEt);
        this.profileNameEt = (TextInputEditText) findViewById(R.id.profileNameEt);

        //sharedprefrence setup
        sharedPreferences = this.getSharedPreferences(Essential.PACKAGE_NAME, Context.MODE_PRIVATE);

        essential = new Essential(this);
    }


    private void setUpFields() {
        String name = sharedPreferences.getString(Essential.NAME_KEY, "");
        profileNameEt.setText(name);
        String age = sharedPreferences.getString(Essential.AGE_KEY, "");
        profileAgeEt.setText(age);
        String gender = sharedPreferences.getString(Essential.GENDER_KEY, "");
        if (gender.equals("male")) {
            profilemaleId.setChecked(true);
        } else if (gender.equals("female")) {
            profilefemaleId.setChecked(true);
        } else {
            profileotherId.setChecked(true);
        }
        String mnum = sharedPreferences.getString(Essential.MOBILE_KEY, "");
        profileMobileEt.setText(mnum);
        String height = sharedPreferences.getString(Essential.HEIGHT_KEY, "");
        profileHeightEt.setText(height);
        String weight = sharedPreferences.getString(Essential.WEIGHT_KEY, "");
        profileWeightEt.setText(weight);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
