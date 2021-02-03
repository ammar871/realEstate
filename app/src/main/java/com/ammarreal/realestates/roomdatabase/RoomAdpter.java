package com.ammarreal.realestates.roomdatabase;

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
import java.util.List;

public class RoomAdpter extends RecyclerView.Adapter<RoomAdpter.MyViewHolder> {

    ArrayList<HomeModel> list;
    Context mcontext;
    private Callback mCallback;

    public ArrayList<HomeModel> getList() {
        return list;
    }

    public void setList(ArrayList<HomeModel> list) {
        this.list = list;
    }

    public Callback getmCallback() {
        return mCallback;
    }

    public void setmCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    AppDatabase database = AppDatabase.getDatabaseInstance(mcontext);

    public RoomAdpter(ArrayList<HomeModel> names, Context mcontext) {
        this.list = names;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        MyViewHolder chatViewHolder = new MyViewHolder(view);
        return chatViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final HomeModel mUser = list.get(position);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice().toString());
        Glide.with(mcontext).load(list.get(position).getImage()).into(holder.image);


        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.onDeleteClick(mUser);
                holder.onBind(position);

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, DetillesActivity.class);
                intent.putExtra("detilles",list.get(position));
                mcontext.startActivity(intent);
            }
        });

    }

    public interface Callback {
        void onDeleteClick(HomeModel mUser);
    }

    public void addItems(List<HomeModel> userList) {
            list = (ArrayList<HomeModel>) userList;
            notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }    }


    public class MyViewHolder extends BaseViewHolder {

        ImageView image, delet;
        TextView name,price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageroom);
            delet = itemView.findViewById(R.id.imageViewDelete);
            name = itemView.findViewById(R.id.nameroom);
            price = itemView.findViewById(R.id.priceroom);

        }

        @Override
        protected void clear() {
            name.setText("");

        }


    }
}

