package net.catdroid.catters.catters.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import net.catdroid.catters.catters.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setViews();
    }

    private void setViews() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPager viewPager = (ViewPager)findViewById(R.id.homeViewPager);
        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(fragmentManager);

        viewPager.setAdapter(homeFragmentPagerAdapter);
    }
}
