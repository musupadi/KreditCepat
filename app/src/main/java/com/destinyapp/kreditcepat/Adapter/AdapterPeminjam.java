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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.destinyapp.kreditcepat.API.ApiRequest;
import com.destinyapp.kreditcepat.API.RetroServer;
import com.destinyapp.kreditcepat.Activity.PeminjamanActivity;
import com.destinyapp.kreditcepat.Model.Method;
import com.destinyapp.kreditcepat.Model.Response.DataModel;
import com.destinyapp.kreditcepat.Model.ResponseModel;
import com.destinyapp.kreditcepat.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPeminjam extends RecyclerView.Adapter<AdapterPeminjam.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;

    String username,nama,email,profile,alamat,level;
    Method method;
    String stat;
    RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    public AdapterPeminjam (Context ctx, List<DataModel> mList,String stat,RecyclerView rv){
        this.ctx = ctx;
        this.mList = mList;
        this.stat = stat;
        this.rv = rv;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_peminjaman,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterPeminjam.HolderData holderData, int posistion) {
        final DataModel dm = mList.get(posistion);
        method=new Method();
        if (stat.equals("PERMINTAAN")){
            if (!dm.getStatus_2().equals("PERMINTAAN")){
                holderData.card.setVisibility(View.GONE);
            }
        }else{
            if (dm.getStatus_2().equals("PERMINTAAN")){
                holderData.card.setVisibility(View.GONE);
            }
        }
        holderData.nama.setText(dm.getNama());
        holderData.pinjam.setText(dm.getJumlah_pinjaman());
        holderData.TPinjam.setText(dm.getTanggal_Transaksi());
        holderData.TBayar.setText(dm.getTanggal_pembayaran());
        holderData.komisi.setText(dm.getKomisi());
        holderData.Total.setText(dm.getTotal_pinjaman());
        if (dm.getStatus().equals("LUNAS")){
            holderData.tolak.setVisibility(View.GONE);
            holderData.terima.setBackgroundResource(R.drawable.button_rounded_green);
            holderData.terima.setText("Lunas");
        }else if(dm.getStatus_2().equals("DITERIMA")){
            holderData.tolak.setVisibility(View.GONE);
            holderData.terima.setBackgroundResource(R.drawable.button_rounded_blue);
            holderData.terima.setText("Diterima");
        }else if (dm.getStatus_2().equals("DITOLAK")){
            holderData.tolak.setVisibility(View.GONE);
            holderData.terima.setBackgroundResource(R.drawable.button_rounded_red);
            holderData.terima.setText("Ditolak");
        }
        holderData.terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dm.getStatus_2().equals("PERMINTAAN")){
                    LOGIC(dm.getId_transaksi(),"BELUM LUNAS",holderData.card);
                }
            }
        });
        holderData.tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGIC(dm.getId_transaksi(),"DITOLAK",holderData.card);
            }
        });
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama,pinjam,TPinjam,TBayar,komisi,Total;
        Button terima,tolak;
        DataModel dm;
        LinearLayout card;
        HolderData(View v){
            super(v);
            nama = v.findViewById(R.id.tvNama);
            terima = v.findViewById(R.id.btnTerima);
            tolak = v.findViewById(R.id.btnTolak);
            pinjam = v.findViewById(R.id.tvJumlahPinjaman);
            TPinjam = v.findViewById(R.id.tvTanggalPinjam);
            TBayar = v.findViewById(R.id.tvTanggalBayar);
            komisi = v.findViewById(R.id.tvKomisi);
            Total = v.findViewById(R.id.tvTotalPinjaman);
            card = v.findViewById(R.id.card_view);
        }
    }
    private void LOGIC(String id, String Status, final LinearLayout card){
        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setMessage("Sedang Mengupdate Data");
        pd.setCancelable(false);
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Logic = api.UpdateTransaksi(id,"BELUM LUNAS","DITERIMA");
        Logic.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        card.setVisibility(View.GONE);
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
    private void Logic2(){
        mAdapter = new AdapterPeminjam(ctx,mList,stat,rv);
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
