package com.destinyapp.kreditcepat.Activity.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.destinyapp.kreditcepat.Activity.CaraPinjamActivity;
import com.destinyapp.kreditcepat.Activity.ProductActivity;
import com.destinyapp.kreditcepat.Activity.TentangActivity;
import com.destinyapp.kreditcepat.Model.Method;
import com.destinyapp.kreditcepat.R;
import com.destinyapp.kreditcepat.SharedPreferance.DB_Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    ImageView ivHeader;
    TextView tvHeader,tvTgl;
    DB_Helper dbHelper;
    String email,nama,telpon,alamat,nik;
    LinearLayout TentangKami,ProdukKami,CaraPinjam;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TentangKami = view.findViewById(R.id.linearTentang);
        ProdukKami = view.findViewById(R.id.linearProduk);
        CaraPinjam = view.findViewById(R.id.linearCaraPinjam);
        dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkSession();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                email = cursor.getString(1);
                nama = cursor.getString(2);
                telpon = cursor.getString(3);
                alamat = cursor.getString(4);
                nik = cursor.getString(5);
            }
        }
        tvTgl=view.findViewById(R.id.tvTgl);
        ivHeader=view.findViewById(R.id.ivHeader);
        tvHeader=view.findViewById(R.id.tvHeader);
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        if (nama!=null){
            if (hour > 4 && hour < 11){
                tvHeader.setText("Selamat Pagi, "+nama);
                ivHeader.setImageResource(R.drawable.morning);
            }else if(hour >= 11 && hour <15){
                tvHeader.setText("Selamat Siang, "+nama);
                ivHeader.setImageResource(R.drawable.afternoon);
            }else if(hour >= 15 && hour <18){
                tvHeader.setText("Selamat Sore, "+nama);
                ivHeader.setImageResource(R.drawable.evening);
            }else{
                tvHeader.setText("Selamat Malam, "+nama);
                ivHeader.setImageResource(R.drawable.night);
            }
        }else{
            if (hour > 4 && hour < 11){
                tvHeader.setText("Selamat Pagi");
                ivHeader.setImageResource(R.drawable.morning);
            }else if(hour >= 11 && hour <15){
                tvHeader.setText("Selamat Siang");
                ivHeader.setImageResource(R.drawable.afternoon);
            }else if(hour >= 15 && hour <18){
                tvHeader.setText("Selamat Sore");
                ivHeader.setImageResource(R.drawable.evening);
            }else{
                tvHeader.setText("Selamat Malam");
                ivHeader.setImageResource(R.drawable.night);
            }
        }

        Method method = new Method();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String thisDay = dateFormat.format(date);
        tvTgl.setText(method.getToday()+", "+thisDay);
        TentangKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TentangActivity.class);
                startActivity(intent);
            }
        });
        ProdukKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                startActivity(intent);
            }
        });
        CaraPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaraPinjamActivity.class);
                startActivity(intent);
            }
        });
    }
}
