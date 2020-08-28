package com.destinyapp.kreditcepat.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.destinyapp.kreditcepat.R;
import com.destinyapp.kreditcepat.SharedPreferance.DB_Helper;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView anim;
    DB_Helper dbHelper;
    String email,nama,telpon,alamat,nik;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
                final MediaPlayer SuaraMe = MediaPlayer.create(MainActivity.this,R.raw.welcome);
                SuaraMe.start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (EasyPermissions.hasPermissions(MainActivity.this, galleryPermissions)) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    EasyPermissions.requestPermissions(MainActivity.this, "Access for storage",
                            101, galleryPermissions);
                }

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
