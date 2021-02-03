package com.ammarreal.realestates.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServicClaint {
    private static final String BASE_URL = "https://youkal.herokuapp.com/api/";
    private static Retrofit retrofi=null;



  private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();





    public  static Retrofit getRetrofi(){
        if (retrofi==null)
        {retrofi=new Retrofit.Builder()

                   .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofi;
    }






}
