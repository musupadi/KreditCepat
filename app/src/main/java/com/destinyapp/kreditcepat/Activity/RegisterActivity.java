package com.destinyapp.kreditcepat.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.destinyapp.kreditcepat.API.ApiRequest;
import com.destinyapp.kreditcepat.API.RetroServer;
import com.destinyapp.kreditcepat.Model.ResponseModel;
import com.destinyapp.kreditcepat.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    LinearLayout Daftar,Kembali;
    EditText email,password,nama,alamat,telpon,nik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Daftar=findViewById(R.id.btnRegister);
        Kembali=findViewById(R.id.btnKembali);
        email=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassword);
        nama=findViewById(R.id.etNama);
        alamat=findViewById(R.id.etAlamat);
        telpon=findViewById(R.id.etTelpon);
        nik=findViewById(R.id.etNik);
        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEmail(email.getText().toString());
            }
        });
        Kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void Logic(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Sedang Mencoba Register");
        pd.setCancelable(false);
        pd.show();
        if (Checker().equals("DONE")){
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> register = api.register(email.getText().toString(),
                    password.getText().toString(),
                    nama.getText().toString(),
                    alamat.getText().toString(),
                    telpon.getText().toString(),
                    nik.getText().toString(),
                    "User");
            register.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    pd.hide();
                    if (response.body().getStatus().equals("failed")){
                        Toast.makeText(RegisterActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            });
        }else{
            Toast.makeText(this, Checker(), Toast.LENGTH_SHORT).show();
        }
    }
    private void CheckEmail(final String email){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Sedang Mengecheck Email yang digunakan");
        pd.setCancelable(false);
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> checker =  api.CheckEmail(email);
        checker.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatus().equals("Success")){
                    Logic();
                }else{
                    Toast.makeText(RegisterActivity.this, "Email sudah digunakan", Toast.LENGTH_SHORT).show();
                }
                pd.hide();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        });

    }
    private String Checker(){
        String message = "DONE";
        if (email.getText().toString().isEmpty()){
            message="Email Tidak boleh kosong";
        }else if(password.getText().toString().isEmpty()){
            message="Password Tidak boleh kosong";
        }else if(nama.getText().toString().isEmpty()){
            message="Nama Tidak boleh kosong";
        }else if(alamat.getText().toString().isEmpty()){
            message="Alamat Tidak boleh kosong";
        }else if(telpon.getText().toString().isEmpty()){
            message="Telepon Tidak boleh kosong";
        }else if(nik.getText().toString().isEmpty()){
            message="NIK Tidak boleh kosong";
        }
        return message;
    }
}
