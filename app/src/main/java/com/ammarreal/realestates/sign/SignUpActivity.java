package com.ammarreal.realestates.sign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ammarreal.realestates.R;
import com.ammarreal.realestates.commen.Commen;
import com.ammarreal.realestates.commen.SessionManagment;
import com.ammarreal.realestates.databinding.ActivitySignUpBinding;
import com.ammarreal.realestates.network.Apiinterface;
import com.ammarreal.realestates.network.WebServicClaint;
import com.ammarreal.realestates.pojo.model.Singupresponse;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
ActivitySignUpBinding binding;
    SessionManagment siessonManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        siessonManger=new SessionManagment(this);
        binding.signsign.setOnClickListener(this);
        binding.loginsin.setOnClickListener(this);
    }







    boolean isvalue() {
        if (binding.editfirstname.getText().toString().isEmpty()) {
            StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.enter_name), Toast.LENGTH_LONG, R.style.mytoasterror).show();

            return false;
        } else if (binding.editalastnem.getText().toString().isEmpty()) {

            StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.enter_last), Toast.LENGTH_LONG, R.style.mytoasterror).show();
            return false;

        } else if (binding.editphone.getText().toString().isEmpty()) {

            StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.enter_phone), Toast.LENGTH_LONG, R.style.mytoasterror).show();
            return false;

        } else if (binding.editemail.getText().toString().isEmpty()) {

            StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.enter_email), Toast.LENGTH_LONG, R.style.mytoasterror).show();
            return false;
        } else if (binding.editpaswword.getText().toString().isEmpty()) {

            StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.enter_pass), Toast.LENGTH_LONG, R.style.mytoasterror).show();
            return false;
        } else {
            return true;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginsin:
                Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(login);
                break;
            case R.id.signsign:
                if (isvalue()) {
                    calldata();

                }

                break;

        }

    }
    private void calldata() {
        final ProgressDialog pd = new ProgressDialog(SignUpActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        if (Commen.isNetworkOnline(getApplicationContext())){
        Apiinterface apiinterface = WebServicClaint.getRetrofi().create(Apiinterface.class);
        Call<Singupresponse> call = apiinterface.regester(binding.editfirstname.getText().toString()
                , binding.editalastnem.getText().toString(),
                binding.editphone.getText().toString(),
                binding.editemail.getText().toString(),
                binding.editpaswword.getText().toString());
        call.enqueue(new Callback<Singupresponse>() {
            @Override
            public void onResponse(Call<Singupresponse> call, Response<Singupresponse> response) {
                if (response.body() != null) {
                    Log.v("response", response.body().getMsg());
                    siessonManger.saveData(binding.editfirstname.getText().toString(),
                            binding.editalastnem.getText().toString(),
                            binding.editphone.getText().toString(),
                            binding.editemail.getText().toString(),
                            binding.editpaswword.getText().toString(),
                            true);
                    pd.dismiss();
                    StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.successsign), Toast.LENGTH_LONG, R.style.mytoast).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.errorsign), Toast.LENGTH_LONG, R.style.mytoast).show();
                    pd.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Singupresponse> call, Throwable t) {
                StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.error_one), Toast.LENGTH_LONG, R.style.mytoasterror).show();

            }
        });
    }else {

            StyleableToast.makeText(SignUpActivity.this, getResources().getString(R.string.network), Toast.LENGTH_LONG, R.style.mytoasterror).show();
            pd.dismiss();
        }

    }

}
