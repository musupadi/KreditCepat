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
import com.destinyapp.kreditcepat.SharedPreferance.DB_Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    LinearLayout login, back;
    EditText Email, Password;
    DB_Helper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DB_Helper(this);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.btnLogin);
        back = findViewById(R.id.btnKembali);
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logic();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void Logic() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Sedang Mengambil Data Ke Server");
        pd.setCancelable(false);
        pd.show();
        final String username = Email.getText().toString();
        final String password = Password.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username atau Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            pd.hide();
        } else {
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> login = api.login(username, password);
            login.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    pd.hide();
                    String ress = response.body().getStatus();
                    if (ress.equals("failed")) {
//                        SessionLoginSucces(username,password);
                        Toast.makeText(LoginActivity.this, "Password yang anda masukan Salah", Toast.LENGTH_SHORT).show();
                    } else {
                        dbHelper.saveSession(
                                response.body().getData().get(0).id,
                                response.body().getData().get(0).email,
                                response.body().getData().get(0).nama,
                                response.body().getData().get(0).telpon,
                                response.body().getData().get(0).alamat,
                                response.body().getData().get(0).nik,
                                response.body().getData().get(0).level);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    pd.hide();
                    Toast.makeText(LoginActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
