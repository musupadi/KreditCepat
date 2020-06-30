package com.destinyapp.kreditcepat.Activity.ui.pinjaman;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.destinyapp.kreditcepat.Activity.RegisterActivity;
import com.destinyapp.kreditcepat.Model.Method;
import com.destinyapp.kreditcepat.R;
import com.destinyapp.kreditcepat.SharedPreferance.DB_Helper;

import java.util.Calendar;
import java.util.Date;

public class PinjamanFragment extends Fragment {
    Spinner Pinjaman,JangkaWaktu;
    Method method;
    TextView Tanggal,Jumlah,Komisi,Total;
    DB_Helper dbHelper;
    String email,nama,telpon,alamat,nik;
    LinearLayout bayar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pinjaman, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Pinjaman=view.findViewById(R.id.spinnerJumlahPinjaman);
        JangkaWaktu=view.findViewById(R.id.spinnerWaktu);
        Tanggal=view.findViewById(R.id.tvWaktuPembayaran);
        Jumlah=view.findViewById(R.id.tvJumlahPinjaman);
        Komisi=view.findViewById(R.id.tvKomisi);
        Total=view.findViewById(R.id.tvTotalPembayaran);
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
        bayar = view.findViewById(R.id.linearBayar);
        if (email == null){
            bayar.setBackgroundResource(R.drawable.button_rounded_disable);
            bayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            bayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Uang akan segera dikirimkan", Toast.LENGTH_SHORT).show();
                }
            });
        }
        method=new Method();
        Pinjaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        JangkaWaktu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private double ifelses(String data){
        Double nilai = 1000000.0;
        if (data.equals("Rp.1.000.000")){
            nilai = 1000000.0;
        }else if(data.equals("Rp.2.000.000")){
            nilai = 2000000.0;
        }else if(data.equals("Rp.3.000.000")){
            nilai = 3000000.0;
        }else if(data.equals("Rp.4.000.000")){
            nilai = 4000000.0;
        }else if(data.equals("Rp.5.000.000")){
            nilai = 5000000.0;
        }else if(data.equals("Rp.6.000.000")){
            nilai = 6000000.0;
        }else if(data.equals("Rp.7.000.000")){
            nilai = 7000000.0;
        }else if(data.equals("Rp.8.000.000")){
            nilai = 8000000.0;
        }
        return nilai;
    }
//    private String ifelsetanggal(String data){
//        String ifelsestanggal = data;
//        if (data.equals("1")){
//            ifelsestanggal = "01";
//        }else if(data.equals("2")){
//            ifelsestanggal = "02";
//        }else if(data.equals("3")){
//            ifelsestanggal = "03";
//        }else if(data.equals("4")){
//            ifelsestanggal = "05";
//        }else if(data.equals("5")){
//            ifelsestanggal = "05";
//        }else if(data.equals("6")){
//            ifelsestanggal = "06";
//        }else if(data.equals("7")){
//            ifelsestanggal = "07";
//        }else if(data.equals("8")){
//            ifelsestanggal = "08";
//        }else if(data.equals("9")){
//            ifelsestanggal = "09";
//        }
//        return ifelsestanggal;
//    }
    private void Logic(){
        Double bunga=ifelses(Pinjaman.getSelectedItem().toString())*0.008*Double.parseDouble(JangkaWaktu.getSelectedItem().toString());
        Jumlah.setText(Pinjaman.getSelectedItem().toString());
        Komisi.setText(method.MagicRP(bunga));
        Total.setText(method.MagicRP(ifelses(Pinjaman.getSelectedItem().toString())+bunga));
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, Integer.parseInt(JangkaWaktu.getSelectedItem().toString()));
        dt = c.getTime();
        Tanggal.setText(method.Tanggal(String.valueOf(dt)));
    }
}
