package com.ammarreal.realestates.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ammarreal.realestates.R;
import com.ammarreal.realestates.commen.Commen;
import com.ammarreal.realestates.databinding.FragmentHomeBinding;
import com.ammarreal.realestates.home.HomeAdpter;
import com.ammarreal.realestates.pojo.HomeModel;
import com.ammarreal.realestates.pojo.model.ViewModelHome;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    View view;
    ViewModelHome viewModelHome;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        view = binding.getRoot();
        if (Commen.isNetworkOnline(getActivity())) {
            viewModelHome = ViewModelProviders.of(this).get(ViewModelHome.class);
            viewModelHome.getPost();
         //recycler
            binding.recyclerHome.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
            binding.recyclerHome.setHasFixedSize(true);

            final HomeAdpter adpter = new HomeAdpter(getActivity());

            viewModelHome.mutableLiveData.observe(this, new Observer<ArrayList<HomeModel>>() {
                @Override
                public void onChanged(ArrayList<HomeModel> homeModels) {
                    adpter.setList(homeModels);
                    binding.recyclerHome.setAdapter(adpter);


                }

            });
        } else {

            StyleableToast.makeText(getActivity(), getResources().getString(R.string.network), Toast.LENGTH_LONG, R.style.mytoasterror).show();

        }
        return view;
    }

}
