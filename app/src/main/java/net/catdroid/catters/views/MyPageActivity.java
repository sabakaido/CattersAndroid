package net.catdroid.catters.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.catdroid.catters.R;
import net.catdroid.catters.fragments.HomeFragmentPagerAdapter;

/**
 * Created by anikaido on 2016/05/07.
 */
public class MyPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Intent intent;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        setViews();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setViews() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPager viewPager = (ViewPager) findViewById(R.id.mypage_view_pager);
        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(fragmentManager);

        viewPager.setAdapter(homeFragmentPagerAdapter);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_home:
                intent = new Intent();
                intent.setClassName(getPackageName(), HomeActivity.class.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                break;
            case R.id.menu_mypage:
                intent = new Intent();
                intent.setClassName(getPackageName(), MyPageActivity.class.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
