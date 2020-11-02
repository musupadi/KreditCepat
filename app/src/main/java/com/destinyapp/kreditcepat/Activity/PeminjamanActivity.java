package com.destinyapp.kreditcepat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.destinyapp.kreditcepat.API.ApiRequest;
import com.destinyapp.kreditcepat.API.RetroServer;
import com.destinyapp.kreditcepat.Adapter.AdapterPeminjam;
import com.destinyapp.kreditcepat.Adapter.AdapterPermintaan;
import com.destinyapp.kreditcepat.Model.Response.DataModel;
import com.destinyapp.kreditcepat.Model.ResponseModel;
import com.destinyapp.kreditcepat.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanActivity extends AppCompatActivity {
    RecyclerView rv;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    Button History,Permintaan;
    String Status = "PERMINTAAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);
        rv=findViewById(R.id.recycler);
        Permintaan = findViewById(R.id.btnPermintaan);
        History = findViewById(R.id.btnHistory);
        History.setBackgroundResource(R.drawable.button_rounded_primary);
        Permintaan.setBackgroundResource(R.drawable.button_rounded_green);
        Permintaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status="PERMINTAAN";
                History.setBackgroundResource(R.drawable.button_rounded_primary);
                Permintaan.setBackgroundResource(R.drawable.button_rounded_green);
                LOGIC();
            }
        });
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status ="HISTORY";
                History.setBackgroundResource(R.drawable.button_rounded_green);
                Permintaan.setBackgroundResource(R.drawable.button_rounded_primary);
                LOGIC();
            }
        });
        LOGIC();
    }
    private void LOGIC(){
        rv.setLayoutManager(new GridLayoutManager(PeminjamanActivity.this, 1));
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Data = api.GetTransaksi();
        Data.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getData();
                mAdapter = new AdapterPeminjam(PeminjamanActivity.this,mItems,Status,rv);
                rv.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(PeminjamanActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}