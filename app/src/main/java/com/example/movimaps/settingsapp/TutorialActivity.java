package com.example.movimaps.settingsapp;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TutorialActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private TutorialPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        initViews();
        setupViewPager();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
    }

    private void setupViewPager() {
        pagerAdapter = new TutorialPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Inicio");
                    break;
                case 1:
                    tab.setText("Ajustes");
                    break;
                case 2:
                    tab.setText("Personalización");
                    break;
            }
        }).attach();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
    }
}
