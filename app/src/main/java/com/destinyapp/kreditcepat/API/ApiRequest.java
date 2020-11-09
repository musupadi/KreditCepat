package com.destinyapp.kreditcepat.API;

import com.destinyapp.kreditcepat.Model.ResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("Login")
    Call<ResponseModel> login(@Field("email") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("Checkemail")
    Call<ResponseModel> CheckEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("Register")
    Call<ResponseModel> register(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("nama") String nama,
                                 @Field("alamat") String alamat,
                                 @Field("telpon") String telpon,
                                 @Field("nik") String nik,
                                 @Field("level") String level);






    @FormUrlEncoded
    @POST("UpdatePembayaran")
    Call<ResponseModel> UpdatePembayaran(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("UpdatePembayaran2")
    Call<ResponseModel> UpdatePembayaran2(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("Permintaan")
    Call<ResponseModel> PostPermintaan(@Field("id_user") String id_user);

    @GET("Permintaan")
    Call<ResponseModel> Permintaan();

    @GET("Transaksi")
    Call<ResponseModel> GetTransaksi();

    @FormUrlEncoded
    @POST("Check_transaksi")
    Call<ResponseModel> CheckPinjaman(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("CheckPembayaran")
    Call<ResponseModel> CheckPembayaran(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("Transaksi")
    Call<ResponseModel> Transaksi(@Field("id_user") String id_user,
                                  @Field("komisi") String komisi,
                                  @Field("jumlah_pinjaman") String jumlah_pinjaman,
                                  @Field("tanggal_transaksi") String tanggal_transaksi,
                                  @Field("tanggal_pembayaran") String tanggal_pembayaran,
                                  @Field("total_pinjaman") String total_pinjaman,
                                  @Field("status") String status);

    @Multipart
    @POST("Bukti")
    Call<ResponseModel> UploadBukti(@Part("id_user") RequestBody id_user,
                                    @Part("id_transaksi") RequestBody id_transaksi,
                                 @Part MultipartBody.Part bukti);

    @FormUrlEncoded
    @POST("UpdateTransaksi")
    Call<ResponseModel> UpdateTransaksi(@Field("id_transaksi") String id_transaksi,
                                  @Field("status") String status,
                                  @Field("status_2") String status2);

}
