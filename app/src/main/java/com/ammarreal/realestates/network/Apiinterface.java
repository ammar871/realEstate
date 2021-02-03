package com.ammarreal.realestates.network;



import com.ammarreal.realestates.pojo.model.Singupresponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Apiinterface {

    @POST("register")
    @FormUrlEncoded
    Call<Singupresponse> regester(@Field("first_name") String fristname,
                                  @Field("last_name") String last_name,
                                  @Field("phone") String phone,
                                  @Field("email") String email,
                                  @Field("password") String pasword);

    @POST("login")
    @FormUrlEncoded
    Call<Singupresponse> login(@Field("email") String email,
                               @Field("password") String pasword);
}
