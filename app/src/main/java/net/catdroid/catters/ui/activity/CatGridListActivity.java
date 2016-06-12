package net.catdroid.catters.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.catdroid.catters.R;
import net.catdroid.catters.ui.fragment.CatFavoriteListFragment;
import net.catdroid.catters.ui.views.adapter.CatViewPagerAdapter;
import net.catdroid.catters.utils.CatUtils;

/**
 * Created by anikaido on 2016/06/11.
 */
public class CatGridListActivity extends CatBaseActivity {

    private TabLayout mTabLayoutCatMain;
    private ViewPager mViewPager;
    private CatViewPagerAdapter mCatViewPagerAdapter;
    private TextView mTextViewOfflineMessageMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initViews();
        if (! CatUtils.isNetwork(getApplicationContext())) {
            mTextViewOfflineMessageMain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == CatViewPagerAdapter.PAGE_ID_FAVORITE_LIST) {
            mViewPager.setCurrentItem(CatViewPagerAdapter.PAGE_ID_CAT_LIST);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        setContentView(R.layout.activity_cat_grid_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        mTextViewOfflineMessageMain = (TextView) findViewById(R.id.textViewOfflineMessageMain);
        mTabLayoutCatMain = (TabLayout) findViewById(R.id.tabLayoutCatMain);
        mViewPager = (ViewPager) findViewById(R.id.viewPagerCatList);
        mCatViewPagerAdapter = new CatViewPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mCatViewPagerAdapter);
        mTabLayoutCatMain.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case CatViewPagerAdapter.PAGE_ID_CAT_LIST:
                        break;
                    case CatViewPagerAdapter.PAGE_ID_FAVORITE_LIST:
                        CatFavoriteListFragment catFavoriteListFragment = (CatFavoriteListFragment) mCatViewPagerAdapter.getItem(position);
                        catFavoriteListFragment.refreshFavorites();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
