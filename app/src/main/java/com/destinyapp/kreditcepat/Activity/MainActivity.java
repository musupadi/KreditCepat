package com.destinyapp.kreditcepat.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.destinyapp.kreditcepat.R;
import com.destinyapp.kreditcepat.SharedPreferance.DB_Helper;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView anim;
    DB_Helper dbHelper;
    String email,nama,telpon,alamat,nik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DB_Helper(MainActivity.this);
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
        if (email != null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        anim = findViewById(R.id.animation);
        anim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

}
