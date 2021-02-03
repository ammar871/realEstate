package com.ammarreal.realestates.pojo.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ammarreal.realestates.pojo.HomeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewModelHome extends ViewModel {
    private ArrayList<HomeModel> list = new ArrayList<>();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = mDatabase.getReference().child("estates");
    public MutableLiveData<ArrayList<HomeModel>> mutableLiveData = new MutableLiveData<>();

    public void getPost() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HomeModel model = dataSnapshot1.getValue(HomeModel.class);
                    list.add(model);
                }
                mutableLiveData.setValue(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    } // POST....
}
