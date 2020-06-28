package com.destinyapp.kreditcepat.API;

import com.destinyapp.kreditcepat.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseModel> login(@Field("email") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("checkemail")
    Call<ResponseModel> CheckEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseModel> register(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("nama") String nama,
                                 @Field("alamat") String alamat,
                                 @Field("telpon") String telpon,
                                 @Field("nik") String nik);
}
