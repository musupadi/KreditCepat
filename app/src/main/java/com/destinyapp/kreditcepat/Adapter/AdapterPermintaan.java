package com.destinyapp.kreditcepat.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.destinyapp.kreditcepat.API.ApiRequest;
import com.destinyapp.kreditcepat.API.RetroServer;
import com.destinyapp.kreditcepat.Activity.PermintaanActivity;
import com.destinyapp.kreditcepat.Model.Method;
import com.destinyapp.kreditcepat.Model.Response.DataModel;
import com.destinyapp.kreditcepat.Model.ResponseModel;
import com.destinyapp.kreditcepat.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPermintaan extends RecyclerView.Adapter<AdapterPermintaan.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;

    String username,nama,email,profile,alamat,level;
    Method method;
    public AdapterPermintaan (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_permintaan,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPermintaan.HolderData holderData, int posistion) {
        final DataModel dm = mList.get(posistion);
        method=new Method();
        holderData.nama.setText(dm.getNama());
        String BASE_URL = ctx.getString(R.string.BASE_URL);
        String URL = BASE_URL+"img/bukti/"+dm.getBukti();
//        Picasso.get().load(URL).into(holderData.gambar);
        Glide.with(ctx)
                .load(URL)
                .into(holderData.gambar);
        holderData.terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ctx, dm.getId_user(), Toast.LENGTH_SHORT).show();
                Logic1(dm.getId_user(),dm.getId_transaksi());
            }
        });
        holderData.tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama;
        ImageView gambar;
        Button terima,tolak;
        DataModel dm;
        HolderData(View v){
            super(v);
            nama = v.findViewById(R.id.tvNama);
            gambar = v.findViewById(R.id.ivBukti);
            terima = v.findViewById(R.id.btnTerima);
            tolak = v.findViewById(R.id.btnTolak);
        }
    }
    private void Logic1(final String id,final String id2){
        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setMessage("Sedang Mengubah Data Pembayaran");
        pd.setCancelable(false);
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Logic = api.UpdatePembayaran(id);
        Logic.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
//                        Toast.makeText(ctx, id, Toast.LENGTH_SHORT).show();
                        Logic2(id,id2);
                    }else{
                        Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ctx, "Terjadi kesalahan pada ="+e.toString(), Toast.LENGTH_SHORT).show();
                }
                pd.hide();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        });
    }
    private void Logic2(final String id,final String id2){
        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setMessage("Sedang Mengubah Data Transaksi");
        pd.setCancelable(false);
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Logic = api.UpdateTransaksi(id2,"LUNAS","DITERIMA");
        Logic.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        Intent intent = new Intent(ctx, PermintaanActivity.class);
                        ctx.startActivity(intent);
                    }else{
                        Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ctx, "Terjadi kesalahan pada ="+e.toString(), Toast.LENGTH_SHORT).show();
                }
                pd.hide();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        });
    }
//    private void Logic3(String id){
//        final ProgressDialog pd = new ProgressDialog(ctx);
//        pd.setMessage("Sedang Mengubah Data Transaksi");
//        pd.setCancelable(false);
//        pd.show();
//        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
//        Call<ResponseModel> Logic = api.UpdateTransaksi(id);
//        Logic.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                try {
//                    if (response.body().getStatus().equals("success")){
//
//                    }else{
//                        Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }catch (Exception e){
//                    Toast.makeText(ctx, "Terjadi kesalahan pada ="+e.toString(), Toast.LENGTH_SHORT).show();
//                }
//                pd.hide();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
//                pd.hide();
//            }
//        });
//    }
}


