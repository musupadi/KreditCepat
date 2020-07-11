package com.destinyapp.kreditcepat.Activity.ui.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.destinyapp.kreditcepat.Activity.About.AboutActivity;
import com.destinyapp.kreditcepat.Activity.HomeActivity;
import com.destinyapp.kreditcepat.Activity.LoginActivity;
import com.destinyapp.kreditcepat.Activity.RegisterActivity;
import com.destinyapp.kreditcepat.R;
import com.destinyapp.kreditcepat.SharedPreferance.DB_Helper;


public class AccountFragment extends Fragment {
    DB_Helper dbHelper;
    String email,nama,telpon,alamat,nik;
    LinearLayout masuk,keluar,daftar,tentang;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        masuk=view.findViewById(R.id.linearMasuk);
        keluar=view.findViewById(R.id.linearKeluar);
        daftar=view.findViewById(R.id.linearDaftar);
        tentang=view.findViewById(R.id.linearTentang);
        dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkSession();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                email = cursor.getString(0);
                nama = cursor.getString(1);
                telpon = cursor.getString(2);
                alamat = cursor.getString(3);
                nik = cursor.getString(4);
            }
        }
        if (nama!=null){
            masuk.setVisibility(View.GONE);
            daftar.setVisibility(View.GONE);
            keluar.setVisibility(View.VISIBLE);
        }else{
            masuk.setVisibility(View.VISIBLE);
            daftar.setVisibility(View.VISIBLE);
            keluar.setVisibility(View.GONE);
        }
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer SuaraMe = MediaPlayer.create(getActivity(),R.raw.out);
                SuaraMe.start();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Anda Yakin ingin Logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                dbHelper = new DB_Helper(getActivity());
                                dbHelper.userLogout(getActivity());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        //Set your icon here
                        .setTitle("Perhatian !!!")
                        .setIcon(R.drawable.ic_close_black_24dp);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
