package com.product.listing;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
//import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.product.listing.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    ProdListAdapter adapter;
    String url = "https://dummyjson.com/products";
    ArrayList<ProdModel>  list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
    }

    private void loadData() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("products");
                            Log.d("responseresponse: ", jsonArray.getJSONObject(1).getString("description"));

                            Log.d("Json response", "onResponse: " + jsonObject);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObj = jsonArray.getJSONObject(i);
                                try {
                                    ProdModel dataSet = new ProdModel();
                                    dataSet.setId(jObj.getInt("id"));
                                    dataSet.setTitle(jObj.getString("title"));
                                    dataSet.setDesc(jObj.getString("description"));
                                    dataSet.setImgUrl(jObj.getString("thumbnail"));
                                    dataSet.setPrice(jObj.getString("price"));
                                    dataSet.setStock(jObj.getString("stock"));
                                    dataSet.setRating(jObj.getString("rating"));
                                    list.add(dataSet);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            setAdapter(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.d("onErrorResponse: ", error.toString()));
        mRequestQueue.add(jsonObjectRequest);
    }

    private void setAdapter(ArrayList<ProdModel> list) {
        RecyclerView recyclerView = findViewById(R.id.prodRV);
        ProdListAdapter adapter = new ProdListAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}