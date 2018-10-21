package com.astudios.disastermanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.BagRecyclerAdapter;
import essential.Essential;
import model.BagItem;

public class GoBagActivity extends AppCompatActivity {

    public android.widget.TextView currentBagWt;
    private android.widget.TextView totalBagWt;
    private RecyclerView recommendedRecycler;
    private RecyclerView additionalRecycler;
    Essential essential;
    private RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    static ArrayList<BagItem> dataList = new ArrayList<>();
    static double weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_bag);

        essential = new Essential(this);
        sharedPreferences = getSharedPreferences(Essential.PACKAGE_NAME, Context.MODE_PRIVATE);
        findIds();

        setData();

    }

    private void setData() {
        essential.showDialog();
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(GoBagActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, Essential.BAG_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        essential.cancelDialog();
                        // essential.show(response, Essential.INFO);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");
                            weight = jsonObject.getDouble("weight");
                            totalBagWt.setText(String.valueOf(weight*1000));

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject job = jsonArray.getJSONObject(i);
                                BagItem bagItem = new BagItem();
                                bagItem.setItemInfo(job.getString("item_desc"));
                                bagItem.setItemTitle(job.getString("item_name"));
                                bagItem.setMaxCount(job.getInt("item_qty_max"));
                                bagItem.setMinCount(job.getInt("item_qty_min"));
                                bagItem.setItemWeight(job.getInt("item_wt"));
                                bagItem.setCurrentCount(bagItem.getMinCount());
                               // essential.show(bagItem.getCurrentCount()+"",Essential.INFO);

                                dataList.add(bagItem);


                            }

                            BagRecyclerAdapter adapter = new BagRecyclerAdapter(GoBagActivity.this, dataList);

                            recommendedRecycler.setLayoutManager(new LinearLayoutManager(GoBagActivity.this));
                            recommendedRecycler.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                essential.cancelDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                String id = sharedPreferences.getString(Essential.ID_KEY, "");
                params.put("id", id);

                return params;
            }
        };

        requestQueue.add(request);


    }

    private void findIds() {

        this.additionalRecycler = (RecyclerView) findViewById(R.id.additionalRecycler);
        this.recommendedRecycler = (RecyclerView) findViewById(R.id.recommendedRecycler);
        this.totalBagWt = (TextView) findViewById(R.id.totalBagWt);
        this.currentBagWt = (TextView) findViewById(R.id.currentBagWt);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void updateText(int val)
    {
        int pre = Integer.parseInt(currentBagWt.getText().toString());
        currentBagWt.setText((val+pre)+"");
    }
    public void modifyText(int val)
    {
        int pre = Integer.parseInt(currentBagWt.getText().toString());
        currentBagWt.setText((pre-val)+"");
    }
}
