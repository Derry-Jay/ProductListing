package com.product.listing;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String url = "https://dummyjson.com/products";
    ArrayList<ProdModel> list = new ArrayList<>();

    boolean isNightModeOn;

    public Button bottomSheetButton;

    public BottomSheetDialog bottomSheetDialog;

    TextView darkMode;

    TextView darkMode2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomSheetButton = findViewById(R.id.button);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {

            isNightModeOn = false;

        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

            isNightModeOn = true;

        }

        bottomSheetButton.setOnClickListener(view -> {
            bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetTheme);
            View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, null);

            darkMode = findViewById(R.id.h1);
            darkMode2 = findViewById(R.id.h5);

            sheetView.findViewById(R.id.h1).setOnClickListener(view1 -> {

                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isNightModeOn = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNightModeOn = true;
                }

            });

            sheetView.findViewById(R.id.h5).setOnClickListener(view12 -> {

                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isNightModeOn = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNightModeOn = true;
                }

            });
            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();
        });
        loadData();
    }

    private void loadData() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
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
                }, error -> Log.d("onErrorResponse: ", error.toString()));
        mRequestQueue.add(jsonObjectRequest);
    }

    private void setAdapter(ArrayList<ProdModel> list) {
        RecyclerView recyclerView = findViewById(R.id.prodRV);
        ProdListAdapter adapter = new ProdListAdapter(this, list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}