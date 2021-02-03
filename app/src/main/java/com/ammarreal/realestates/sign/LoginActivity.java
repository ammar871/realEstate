package com.ammarreal.realestates.sign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ammarreal.realestates.R;
import com.ammarreal.realestates.commen.Commen;
import com.ammarreal.realestates.commen.SessionManagment;
import com.ammarreal.realestates.databinding.ActivityLoginBinding;
import com.ammarreal.realestates.home.Home;
import com.ammarreal.realestates.network.Apiinterface;
import com.ammarreal.realestates.network.WebServicClaint;
import com.ammarreal.realestates.pojo.model.Singupresponse;
import com.muddzdev.styleabletoast.StyleableToast;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    SessionManagment siessonManger;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        siessonManger = new SessionManagment(this);
        binding.lologin.setOnClickListener(this);
        binding.sinlogin.setOnClickListener(this);
        Paper.init(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sinlogin:
                Intent homlogin = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(homlogin);


                break;
            case R.id.lologin:


                if (oncheck()) {
                    calllogin();
                    // loadData();
                }


        }


    }

    private boolean oncheck() {

        if ((binding.editemaillogin.getText().toString().isEmpty())) {
            StyleableToast.makeText(LoginActivity.this, getResources().getString(R.string.enter_name), Toast.LENGTH_LONG, R.style.mytoast).show();
            return false;
        } else if ((binding.editpasswordlogin.getText().toString().isEmpty())) {

            StyleableToast.makeText(LoginActivity.this, getResources().getString(R.string.enter_name), Toast.LENGTH_LONG, R.style.mytoast).show();
            return false;

        } else {

            return true;
         /*Intent intent = new Intent(LoginActivity.this, Home.class);
         //   intent.putExtra("name",name.getText().toString());
         //  intent.putExtra("pasword",pasword.getText().toString());
         startActivity(intent);*/
        }

    }

    private void calllogin() {
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        if (Commen.isNetworkOnline(getApplicationContext())) {
            if (binding.chekbox.isChecked()) {
                Paper.book().write(Commen.USERKEY, binding.editemaillogin.getText().toString());
                Paper.book().write(Commen.USER_PASS, binding.editpasswordlogin.getText().toString());
            }

            Apiinterface apiinterface = WebServicClaint.getRetrofi().create(Apiinterface.class);
            Call<Singupresponse> call = apiinterface.login(binding.editemaillogin.getText().toString(),
                    binding.editpasswordlogin.getText().toString());
            call.enqueue(new Callback<Singupresponse>() {
                @Override
                public void onResponse(Call<Singupresponse> call, Response<Singupresponse> response) {
                    if (response.body() != null) {

                        StyleableToast.makeText(LoginActivity.this, getResources().getString(R.string.success), Toast.LENGTH_LONG, R.style.mytoast).show();
                        pd.dismiss();
                        Intent intent = new Intent(LoginActivity.this, Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        StyleableToast.makeText(LoginActivity.this, getResources().getString(R.string.error_one), Toast.LENGTH_LONG, R.style.mytoasterror).show();
                        pd.dismiss();
                    }
                }


                @Override
                public void onFailure(Call<Singupresponse> call, Throwable t) {
                    StyleableToast.makeText(LoginActivity.this, getResources().getString(R.string.errer_two), Toast.LENGTH_LONG, R.style.mytoasterror).show();

                }
            });


        } else {
            pd.dismiss();

            StyleableToast.makeText(LoginActivity.this, getResources().getString(R.string.network), Toast.LENGTH_LONG, R.style.mytoasterror).show();
        }
    }



}
