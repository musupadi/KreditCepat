package com.destinyapp.kreditcepat.Activity.About;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.destinyapp.kreditcepat.Adapter.TabPagerAdapter;
import com.destinyapp.kreditcepat.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class AboutActivity extends AppCompatActivity {
    private TabLayout Table;
    private AppBarLayout appBar;
    private ViewPager viewPager;
    private FragmentActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Table = findViewById(R.id.tableLayout);
        appBar = findViewById(R.id.appBarId);
        viewPager = findViewById(R.id.viewpager);
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new TabDetail(),"Detail");
        adapter.AddFragment(new TabDeskripsi(),"Deskripsi");
        viewPager.setAdapter(adapter);
        Table.setupWithViewPager(viewPager);
    }
}