package com.destinyapp.kreditcepat.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.destinyapp.kreditcepat.R;

public class CaraPinjamActivity extends AppCompatActivity {
    ImageView bagaimana,klienlama,perpanjang,pelunasan;
    LinearLayout lBagaimana,lKlienlama,lPerpanjang,lPelunasan;
    Boolean Bagaimana = false;
    Boolean KlienLama = false;
    Boolean Perpanjang = false;
    Boolean Pelunasan = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_pinjam);
        bagaimana=findViewById(R.id.ivBagaimana);
        klienlama=findViewById(R.id.ivBagaimanaKlienLama);
        perpanjang=findViewById(R.id.ivBagaimanaPerpanjang);
        pelunasan=findViewById(R.id.ivBagaimanaPelunasan);
        lBagaimana=findViewById(R.id.linearBagaimana);
        lKlienlama=findViewById(R.id.linearBagaimanaKlienLama);
        lPerpanjang=findViewById(R.id.linearBagaimanaPerpanjang);
        lPelunasan=findViewById(R.id.linearBagaimanaPelunasan);
        bagaimana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Bagaimana){
                    Bagaimana=false;
                }else{
                    Bagaimana=true;
                }
                logic();
            }
        });
        klienlama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (KlienLama){
                    KlienLama=false;
                }else{
                    KlienLama=true;
                }
                logic();
            }
        });
        perpanjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Perpanjang){
                    Perpanjang=false;
                }else{
                    Perpanjang=true;
                }
                logic();
            }
        });
        pelunasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pelunasan){
                    Pelunasan=false;
                }else{
                    Pelunasan=true;
                }
                logic();
            }
        });
    }
    private void logic(){
        if (Bagaimana){
            lBagaimana.setVisibility(View.VISIBLE);
            bagaimana.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }else{
            lBagaimana.setVisibility(View.GONE);
            bagaimana.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        }
        if (KlienLama){
            lKlienlama.setVisibility(View.VISIBLE);
            klienlama.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }else{
            lKlienlama.setVisibility(View.GONE);
            klienlama.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        }
        if (Perpanjang){
            lPerpanjang.setVisibility(View.VISIBLE);
            perpanjang.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }else{
            lPerpanjang.setVisibility(View.GONE);
            perpanjang.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        }
        if (Pelunasan){
            lPelunasan.setVisibility(View.VISIBLE);
            pelunasan.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }else{
            lPelunasan.setVisibility(View.GONE);
            pelunasan.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        }
    }
}
