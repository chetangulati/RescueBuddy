package com.astudios.disastermanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private android.support.design.widget.TextInputEditText emailEt;
    private android.support.design.widget.TextInputEditText passEt;
    private android.widget.Button userLoginBt;
    private android.widget.TextView loginRegisterTv;

    public Essential essential;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //setup all ids
        findIds();

        //autologin
        autoLogin();

    }

    //set up all ids
    private void findIds() {
        essential = new Essential(this);

        this.loginRegisterTv = (TextView) findViewById(R.id.loginRegisterTv);
        loginRegisterTv.setOnClickListener(this);

        this.userLoginBt = (Button) findViewById(R.id.userLoginBt);
        userLoginBt.setOnClickListener(this);

        this.passEt = (TextInputEditText) findViewById(R.id.passEt);
        this.emailEt = (TextInputEditText) findViewById(R.id.emailEt);

        //sharedprefrence setup
        sharedPreferences = this.getSharedPreferences(Essential.PACKAGE_NAME, Context.MODE_PRIVATE);
    }


    private void autoLogin() {
        //check if the username and password is present in shared prefrences or not
        //if present and valid then login and then move forward to homescreen else continue


        String sharedEmail = sharedPreferences.getString(Essential.EMAIL_KEY, "");
        String sharedPass = sharedPreferences.getString(Essential.PASS_KEY, "");


        if (!sharedEmail.equals("") && !sharedPass.equals("")) //which means data is not equal to null
        {
            emailEt.setText(sharedEmail);
            passEt.setText(sharedPass);
            //login(sharedEmail, sharedPass);
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userLoginBt:
                if (essential.checkInternet()) {
                    String email = emailEt.getText().toString().toLowerCase().trim();
                    String pass = passEt.getText().toString();

                    if (email.equals("")) {
                        emailEt.setError(Essential.REQUIRED);
                        return;
                    }
                    if (pass.equals("")) {
                        passEt.setError(Essential.REQUIRED);
                        return;
                    }
                    login(email, pass);
                } else
                    essential.show(Essential.NO_INTERNET, Essential.ERROR);

                break;
            case R.id.loginRegisterTv:
                //move to register activity
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }

    private void login(final String email, final String pass) {
        essential.showDialog();
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, Essential.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        essential.cancelDialog();
                       // essential.show(response,Essential.INFO);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //essential.show(jsonObject.toString(), Essential.SUCCESS);

                            String name = jsonObject.getString(Essential.NAME_KEY);
                            String id = jsonObject.getString(Essential.ID_KEY);
                            String age = jsonObject.getString(Essential.AGE_KEY);
                            String pass = jsonObject.getString(Essential.PASS_KEY);
                            String height = jsonObject.getString(Essential.HEIGHT_KEY);
                            String weight = jsonObject.getString(Essential.WEIGHT_KEY);
                            String email = jsonObject.getString(Essential.EMAIL_KEY);
                            String gender = jsonObject.getString(Essential.GENDER_KEY);
                            String mbnum = jsonObject.getString(Essential.MOBILE_KEY);

                            //adding email and pass to shared preference
                            sharedPreferences.edit().putString(Essential.EMAIL_KEY, email).apply();
                            sharedPreferences.edit().putString(Essential.PASS_KEY, pass).apply();
                            sharedPreferences.edit().putString(Essential.AGE_KEY, age).apply();
                            sharedPreferences.edit().putString(Essential.HEIGHT_KEY, height).apply();
                            sharedPreferences.edit().putString(Essential.WEIGHT_KEY,weight).apply();
                            sharedPreferences.edit().putString(Essential.GENDER_KEY, gender).apply();
                            sharedPreferences.edit().putString(Essential.ID_KEY, id).apply();
                            sharedPreferences.edit().putString(Essential.NAME_KEY, name).apply();
                            sharedPreferences.edit().putString(Essential.MOBILE_KEY, mbnum).apply();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();

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

                params.put(Essential.EMAIL_KEY, email);
                params.put(Essential.PASS_KEY, pass);

                return params;
            }
        };

        requestQueue.add(request);
    }
}
