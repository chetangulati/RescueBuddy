package com.astudios.disastermanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import essential.Essential;
import model.DisasterInfo;

public class DisasterInfoActivity extends AppCompatActivity {

    public static ArrayList<DisasterInfo> disasterData = new ArrayList<>();
    Essential essential;
    private RequestQueue requestQueue;
    private android.widget.ListView disILv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_info);
        this.disILv = (ListView) findViewById(R.id.disILv);
        disILv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DisasterInfoActivity.this, DisasterDetailActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });

        essential = new Essential(this);
        requestDataFromServer();

    }

    private void requestDataFromServer() {
        essential.showDialog();

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, Essential.DISASTER_INFO_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        essential.cancelDialog();
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("doc");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobj = (JSONObject) jsonArray.get(i);

                                JSONArray shelterArr = jobj.getJSONArray("shelter_steps");
                                StringBuilder sb1 = new StringBuilder();
                                for (int j = 0; j < shelterArr.length(); j++) {
                                    sb1.append(shelterArr.getString(j))
                                            .append("\n");

                                }
                                JSONArray evacArr = jobj.getJSONArray("evac_steps");

                                StringBuilder sb2 = new StringBuilder();
                                for (int j = 0; j < evacArr.length(); j++) {
                                    sb2.append(evacArr.getString(j))
                                            .append("\n");
                                }
                                String name = jobj.getString("disaster_name");
                                String video = jobj.getString("disaster_video");
                                String disDes = jobj.getString("disaster_desc");
                                String img = jobj.getString("disaster_image");
                                String bgimg = jobj.getString("disaster_background_image");

                                DisasterInfo disasterInfo = new DisasterInfo();
                                disasterInfo.setDisasterName(name);
                                disasterInfo.setDisasterDesc(disDes);
                                disasterInfo.setVideoLink(video);
                                disasterInfo.setDisasterImage(img);
                                disasterInfo.setEvacSteps(sb2.toString());
                                disasterInfo.setShelterSteps(sb1.toString());
                                disasterInfo.setBgImage(bgimg);

                                disasterData.add(disasterInfo);


                            }

                            String[] arr = new String[disasterData.size()];
                            for (int i = 0; i < arr.length; i++) {
                                arr[i] = new String(disasterData.get(i).getDisasterName());
                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(DisasterInfoActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, arr);

                            disILv.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        essential.cancelDialog();

                    }
                }
        );

        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(this);
// add it to the RequestQueue
        requestQueue.add(getRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
