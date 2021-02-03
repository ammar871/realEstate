package com.ammarreal.realestates.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ammarreal.realestates.R;
import com.ammarreal.realestates.commen.Commen;
import com.ammarreal.realestates.databinding.FragmentAddRealBinding;
import com.ammarreal.realestates.home.Home;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;

import static android.app.Activity.RESULT_OK;


public class Add_Real_Fragment extends Fragment {
    private DatabaseReference dbref;
    private FirebaseDatabase db;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private View view;
    private Uri saveuri;
    private final int PICK_IMAGE = 71;
    private FragmentAddRealBinding binding;

    public Add_Real_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add__real_, container, false);
        view = binding.getRoot();

//Fire Base
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference().child(Commen.DATA_REF);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        binding.imageres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        binding.btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });


        return view;
    }

    private void ChooseImage() {
        Intent glary = new Intent(Intent.ACTION_GET_CONTENT);
        glary.setType("image/*");
        glary.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(glary, "Selecet pictur"), PICK_IMAGE);


    }

    private void startPosting() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("جاري التحميل...");
        pd.show();
        if (Commen.isNetworkOnline(getActivity())) {
            final String name = binding.name.getText().toString().trim();

            final String descre = binding.Descrp.getText().toString().trim();
            final String price = binding.priceed.getText().toString().trim();
            final String size = binding.sizeed.getText().toString().trim();
            final String address = binding.adressed.getText().toString().trim();
            final String phone = binding.phone.getText().toString().trim();

            if (!TextUtils.isEmpty(descre) && !TextUtils.isEmpty(price)
                    && !TextUtils.isEmpty(size)
                    && !TextUtils.isEmpty(address)
                    && saveuri != null) {
                final StorageReference filepath = storageReference.child("images").child(saveuri.getLastPathSegment());

                filepath.putFile(saveuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                DatabaseReference newpost = dbref.push();
                                newpost.child("name").setValue(name);
                                newpost.child("desc").setValue(descre);
                                newpost.child("size").setValue(size);
                                newpost.child("price").setValue(price);
                                newpost.child("addresse").setValue(address);
                                newpost.child("phone").setValue(phone);
                                newpost.child("image").setValue(url);
                                StyleableToast.makeText(getActivity(), getResources().getString(R.string.added_ad), Toast.LENGTH_LONG, R.style.mytoasterror).show();
                                startActivity(new Intent(getActivity(), Home.class));
                            }
                        });

                    }
                });

            } else {
                pd.dismiss();
                StyleableToast.makeText(getActivity(), getResources().getString(R.string.enter_name), Toast.LENGTH_LONG, R.style.mytoasterror).show();
            }
        } else {
            pd.dismiss();
            StyleableToast.makeText(getActivity(), getResources().getString(R.string.network), Toast.LENGTH_LONG, R.style.mytoasterror).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data
                != null && data.getData() != null) {
            saveuri = data.getData();
            binding.imageres.setImageURI(saveuri);
        }
    }
}
