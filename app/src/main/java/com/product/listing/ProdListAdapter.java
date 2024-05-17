package com.product.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.product.listing.R;

import java.util.ArrayList;

public class ProdListAdapter extends RecyclerView.Adapter<ProdListAdapter.ViewHolder> {
    static ArrayList<ProdModel> list;
    static Context context;
    static ProdModel obj;

    public ProdListAdapter(MainActivity mainActivity, ArrayList<ProdModel> list) {
        context = mainActivity;
        ProdListAdapter.list = list;
    }

    @NonNull
    @Override
    public ProdListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_product_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdListAdapter.ViewHolder holder, int position) {
        obj = list.get(position);
        holder.prod_title.setText(obj.getTitle());
        holder.prod_description.setText(obj.getDesc());
        holder.prod_description.setText(obj.getDesc());
        Glide.with(context)
                .load(obj.geImgUrl())
                .into(holder.prod_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView prod_img;
        public TextView prod_title, prod_description;

        public ViewHolder(View itemView) {
            super(itemView);
            this.prod_img = itemView.findViewById(R.id.prod_img);
            this.prod_title = itemView.findViewById(R.id.prod_title);
            this.prod_description = itemView.findViewById(R.id.prod_description);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailedActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("prodTitle", list.get(getAdapterPosition()).getTitle());
                bundle.putString("prodDescription", list.get(getAdapterPosition()).getDesc());
                bundle.putString("prodImgUrl", list.get(getAdapterPosition()).geImgUrl());
                bundle.putString("prodPrice", list.get(getAdapterPosition()).getPrice());
                bundle.putString("prodStock", list.get(getAdapterPosition()).getStock());
                bundle.putString("prodRating", list.get(getAdapterPosition()).getRating());
                intent.putExtras(bundle);
                context.startActivity(intent);
            });
        }
    }
}
