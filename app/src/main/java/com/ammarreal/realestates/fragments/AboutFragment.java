package com.ammarreal.realestates.fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ammarreal.realestates.R;
import com.ammarreal.realestates.databinding.FragmentAboutBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends AppCompatDialogFragment {

FragmentAboutBinding binding;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_about, container, false);
        View view=binding.getRoot();
        binding.okAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

}
