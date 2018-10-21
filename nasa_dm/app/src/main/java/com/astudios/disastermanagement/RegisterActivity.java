package com.astudios.disastermanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import essential.Essential;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private android.support.design.widget.TextInputEditText registerNameEt;
    private android.support.design.widget.TextInputEditText registerAgeEt;
    private android.widget.RadioGroup genderRg;
    private android.support.design.widget.TextInputEditText registerEmailEt;
    private android.support.design.widget.TextInputEditText registerPassEt;
    private android.support.design.widget.TextInputEditText registerMobileEt;
    private android.support.design.widget.TextInputEditText registerHeightEt;
    private android.support.design.widget.TextInputEditText registerWeightEt;
    private android.widget.Button registerRegisterBt;
    private android.widget.TextView registerLoginTv;
    private SharedPreferences sharedPreferences;

    public Essential essential;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //setup ids
        findIds();

    }

    private void findIds() {
        essential = new Essential(this);
        this.registerLoginTv = (TextView) findViewById(R.id.registerLoginTv);
        registerLoginTv.setOnClickListener(this);

        this.registerRegisterBt = (Button) findViewById(R.id.registerRegisterBt);
        registerRegisterBt.setOnClickListener(this);

        this.registerWeightEt = (TextInputEditText) findViewById(R.id.registerWeightEt);
        this.registerHeightEt = (TextInputEditText) findViewById(R.id.registerHeightEt);
        this.registerMobileEt = (TextInputEditText) findViewById(R.id.registerMobileEt);
        this.registerPassEt = (TextInputEditText) findViewById(R.id.registerPassEt);
        this.registerEmailEt = (TextInputEditText) findViewById(R.id.registerEmailEt);
        this.genderRg = (RadioGroup) findViewById(R.id.genderRg);
        this.registerAgeEt = (TextInputEditText) findViewById(R.id.registerAgeEt);
        this.registerNameEt = (TextInputEditText) findViewById(R.id.registerNameEt);
        //sharedprefrence setup
        sharedPreferences = this.getSharedPreferences(Essential.PACKAGE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerRegisterBt:
                if (essential.checkInternet()) {

                    String name = registerNameEt.getText().toString().toLowerCase().trim();
                    String age = registerAgeEt.getText().toString();
                    RadioButton chkdRb = (RadioButton) findViewById(genderRg.getCheckedRadioButtonId());
                    String gender = chkdRb.getText().toString().toLowerCase().trim();
                    String email = registerEmailEt.getText().toString().toLowerCase().trim();
                    String pass = registerPassEt.getText().toString();
                    String mobile = registerMobileEt.getText().toString();
                    String height = registerHeightEt.getText().toString();
                    String weight = registerWeightEt.getText().toString();

                    if (name.equals("")) {
                        registerNameEt.setError(Essential.REQUIRED);
                        return;
                    }
                    if (age.equals("")) {
                        registerAgeEt.setError(Essential.REQUIRED);
                        return;
                    }
                    if (email.equals("")) {
                        registerEmailEt.setError(Essential.REQUIRED);
                        return;
                    }
                    if (pass.equals("")) {
                        registerPassEt.setError(Essential.REQUIRED);
                        return;
                    }
                    if (mobile.equals("")) {
                        registerMobileEt.setError(Essential.REQUIRED);
                        return;
                    }
                    if (height.equals("")) {
                        registerHeightEt.setError(Essential.REQUIRED);
                        return;
                    }
                    if (weight.equals("")) {
                        registerWeightEt.setError(Essential.REQUIRED);
                        return;
                    }

                    //if register true move to home
                    register(name, age, gender, email, pass, mobile, height, weight);

                } else
                    essential.show(Essential.NO_INTERNET, Essential.ERROR);

                essential.cancelDialog();
                break;
            case R.id.registerLoginTv:
                //go back
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

    }

    private void register(final String name, final String age, final String gender, final String email, final String pass, final String mobile, final String height, final String weight) {

        essential.showDialog();
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, Essential.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String id = jsonObject.getString(Essential.ID_KEY);
                            if (!id.equals("")) {

                                //essential.show(jsonObject.toString(), Essential.SUCCESS);
                                //adding email and pass to shared preference
                                sharedPreferences.edit().putString(Essential.EMAIL_KEY, email).apply();
                                sharedPreferences.edit().putString(Essential.PASS_KEY, pass).apply();
                                sharedPreferences.edit().putString(Essential.AGE_KEY, age).apply();
                                sharedPreferences.edit().putString(Essential.HEIGHT_KEY, height).apply();
                                sharedPreferences.edit().putString(Essential.WEIGHT_KEY, weight).apply();
                                sharedPreferences.edit().putString(Essential.GENDER_KEY, gender).apply();
                                sharedPreferences.edit().putString(Essential.ID_KEY, id).apply();
                                sharedPreferences.edit().putString(Essential.NAME_KEY, name).apply();
                                sharedPreferences.edit().putString(Essential.MOBILE_KEY, mobile).apply();

                                essential.cancelDialog();
                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                finish();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            essential.cancelDialog();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ig", error.toString());
                essential.show(error.toString(), Essential.ERROR);
                essential.cancelDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put(Essential.NAME_KEY, name);
                params.put(Essential.AGE_KEY, age);
                params.put(Essential.GENDER_KEY, gender);
                params.put(Essential.EMAIL_KEY, email);
                params.put(Essential.PASS_KEY, pass);
                params.put(Essential.MOBILE_KEY, mobile);
                params.put(Essential.HEIGHT_KEY, height);
                params.put(Essential.WEIGHT_KEY, weight);


                return params;
            }
        };

        requestQueue.add(request);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
