package com.ammarreal.realestates.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ammarreal.realestates.R;
import com.ammarreal.realestates.databinding.FragmentFavoraiteBinding;
import com.ammarreal.realestates.pojo.HomeModel;
import com.ammarreal.realestates.roomdatabase.AppDatabase;
import com.ammarreal.realestates.roomdatabase.RoomAdpter;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;


public class FavoraiteFragment extends Fragment implements RoomAdpter.Callback {

    private AppDatabase database;
    private RoomAdpter mUserAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentFavoraiteBinding binding;
  //  private RecyclerView recyclerView;

    public FavoraiteFragment() {

    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favoraite, container, false);
        view=binding.getRoot();
        database = AppDatabase.getDatabaseInstance(getActivity());

        setUp();

        return view;
    }

    private void setUp() {

       // recyclerView = view.findViewById(R.id.reclcer_fav);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.reclcerFav.setLayoutManager(mLayoutManager);
        binding.reclcerFav.setItemAnimator(new DefaultItemAnimator());
        mUserAdapter = new RoomAdpter(new ArrayList<HomeModel>(), getActivity());
        mUserAdapter.setmCallback(this);
       binding.reclcerFav.setAdapter(mUserAdapter);

    }

    @Override
    public void onDeleteClick(HomeModel mUser) {

        database.userDao().delete(mUser);
        mUserAdapter.addItems(database.userDao().getAll());


        StyleableToast.makeText(getActivity(), getResources().getString(R.string.deleted), Toast.LENGTH_LONG, R.style.mytoasterror).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mUserAdapter.addItems(database.userDao().getAll());

    }

    @Override
    public void onResume() {
        super.onResume();
        mUserAdapter.addItems(database.userDao().getAll());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }


}
