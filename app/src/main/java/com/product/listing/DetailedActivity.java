package com.product.listing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
//import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.samco.listingproductapplication.R;

public class DetailedActivity extends AppCompatActivity {
    TextView title, desc,price_txt,stock_txt,rating_txt;
    String heading ,content,imgUrl,price,stock,rating;
    ImageView back_btn,img;
    CardView exploreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            heading = bundle.getString("prodTitle");
            content = bundle.getString("prodDescription");
            imgUrl = bundle.getString("prodImgUrl");
            price = bundle.getString("prodPrice");
            stock = bundle.getString("prodStock");
            rating = bundle.getString("prodRating");
        }
        price_txt = findViewById(R.id.price_txt);
        stock_txt = findViewById(R.id.stock_txt);
        rating_txt = findViewById(R.id.rating_txt);
        title = findViewById(R.id.title_txt);
        desc = findViewById(R.id.content);
        back_btn = findViewById(R.id.back_btn);
        img = findViewById(R.id.layoutTop);
        exploreBtn = findViewById(R.id.exploreBtn);

        title.setText(heading);
        desc.setText(content);
        price_txt.setText(price);
        stock_txt.setText(stock);
        rating_txt.setText(rating);
        Glide.with(this)
                .load(imgUrl)
                .into(img);

        back_btn.setOnClickListener(view -> finish());
        exploreBtn.setOnClickListener(view -> Toast.makeText(DetailedActivity.this, "Explore More....", Toast.LENGTH_SHORT).show());
    }
}