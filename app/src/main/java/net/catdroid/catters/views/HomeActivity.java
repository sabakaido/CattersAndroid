package net.catdroid.catters.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import net.catdroid.catters.R;
import net.catdroid.catters.fragments.ExpandedImageFragment;
import net.catdroid.catters.fragments.HomeFragment;
import net.catdroid.catters.fragments.MyPageFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ImageView selectedImage;

    private FragmentManager manager;
    ActionBar actionBar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        manager = getSupportFragmentManager();
        Fragment fragment = new HomeFragment();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.home_frame, fragment);
        transaction.commit();

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        TransitionSet ts = new TransitionSet();
        ts.addTransition(new ChangeBounds())
                .addTransition(new ChangeImageTransform())
                .setOrdering(TransitionSet.ORDERING_SEQUENTIAL);

        Fragment fragment;

        switch (id) {
            case R.id.menu_home:
                fragment = new HomeFragment();
                fragment.setSharedElementEnterTransition(ts);

                manager.beginTransaction()
                        .replace(R.id.home_frame, fragment)
                        .commit();
                break;
            case R.id.menu_mypage:
                fragment = new MyPageFragment();
                fragment.setSharedElementEnterTransition(ts);

                manager.beginTransaction()
                        .replace(R.id.home_frame, fragment)
                        .commit();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void flagment(View view, String url) {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.base_transition);
        Transition explodeTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);

        manager.getFragments().get(0).setSharedElementReturnTransition(transition);

        Fragment fragment = new ExpandedImageFragment();
        fragment.setSharedElementEnterTransition(transition);
        fragment.setEnterTransition(explodeTransform);

        view.setTransitionName("cat");

        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);

        FragmentTransaction ft = manager
                .beginTransaction()
                .replace(R.id.home_frame, fragment)
                .addToBackStack(HomeFragment.TAG)
                .addSharedElement(view, "cat");

        ft.commit();
    }
}