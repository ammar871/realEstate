package com.ammarreal.realestates.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ammarreal.realestates.BuildConfig;
import com.ammarreal.realestates.R;
import com.ammarreal.realestates.commen.SessionManagment;
import com.ammarreal.realestates.databinding.FragmentSittingBinding;

import io.paperdb.Paper;

public class SittingFragment extends Fragment implements View.OnClickListener {

    private SessionManagment msessionManagment;
    FragmentSittingBinding binding;

    public SittingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sitting, container, false);
        View view = binding.getRoot();
        msessionManagment = new
                SessionManagment(getActivity());
        Paper.init(getActivity());

        binding_onclick();
        return view;
    }

    private void binding_onclick() {
        binding.aboutApp.setOnClickListener(this);
        binding.shareApp.setOnClickListener(this);
        binding.logOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.log_out:
                msessionManagment.logOut();
                Paper.book().destroy();


                break;

            case R.id.share_app:
                shar(getActivity());
                break;

            case R.id.about_app:
                AboutFragment aboutFragment = new AboutFragment();
                aboutFragment.show(getActivity().getSupportFragmentManager(), null);
                break;
            default:

        }
    }

    public static void shar(Context context){

        try {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareMessage= "\nتطبيق بيع العقارات  \n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(sharingIntent, "تطبيق بيع العقارات"));
        } catch(Exception e) {

            Toast.makeText(context, "فشلت العملية", Toast.LENGTH_SHORT).show();
        }
    }


}
