package com.ammarreal.realestates.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.ammarreal.realestates.R;
import com.ammarreal.realestates.detilles.DetillesActivity;
import com.ammarreal.realestates.pojo.HomeModel;

import java.util.ArrayList;

public class HomeAdpter extends RecyclerView.Adapter<HomeAdpter.HomeViewHolder> {

    ArrayList<HomeModel>list=new ArrayList<>();

    Context mcontext;



    public ArrayList<HomeModel> getList() {
        return list;
    }


    public HomeAdpter( Context mcontext) {
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        HomeViewHolder chatViewHolder = new HomeViewHolder(view);
        return chatViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.pric.setText(mcontext.getResources().getString(R.string.price)+" :" + list.get(position).getPrice());
        holder.address.setText(list.get(position).getAddresse());
        Glide.with(mcontext).load(list.get(position).getImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, DetillesActivity.class);
                intent.putExtra("detilles",list.get(position));
                mcontext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setList(ArrayList<HomeModel> list) {
        this.list = list;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

ImageView image;
TextView name,pric,address;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image_item);
            name=itemView.findViewById(R.id.nameitem);
            pric=itemView.findViewById(R.id.price_item);
            address=itemView.findViewById(R.id.address_item);


        }


    }
}

