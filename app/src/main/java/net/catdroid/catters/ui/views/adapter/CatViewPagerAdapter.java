package net.catdroid.catters.ui.views.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.catdroid.catters.R;
import net.catdroid.catters.ui.fragment.CatFavoriteListFragment;
import net.catdroid.catters.ui.fragment.CatListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anikaido on 2016/06/11.
 */
public class CatViewPagerAdapter extends FragmentPagerAdapter {

    public static final int PAGE_ID_CAT_LIST = 0;
    public static final int PAGE_ID_FAVORITE_LIST = 1;

    private List<Fragment> mFragments;
    private List<String> mPageTitles;

    public CatViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        mFragments = new ArrayList<>();
        mFragments.add(CatListFragment.newInstance());
        mFragments.add(CatFavoriteListFragment.newInstance());

        mPageTitles = new ArrayList<>();
        mPageTitles.add(context.getString(R.string.cat_list));
        mPageTitles.add(context.getString(R.string.favorite_list));
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPageTitles.get(position);
    }
}
