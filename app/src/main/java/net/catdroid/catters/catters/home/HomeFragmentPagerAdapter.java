package net.catdroid.catters.catters.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.catdroid.catters.catters.home.dynamicview.DynamicListViewFragment;
import net.catdroid.catters.catters.home.staticview.StaticListViewFragment;

/**
 * Created by anikaido on 2016/03/06.
 */
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String CAT_APARTMENT = "http://catroid.net:3000/get/CatApartment";
    private static final String RT_IF_YOU_LIKE = "http://catroid.net:3000/get/cutegirls_type";

    public HomeFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StaticListViewFragment();
            case 1:
                return new StaticListViewFragment();
            case 2:
                return new DynamicListViewFragment(CAT_APARTMENT);
            case 3:
                return new DynamicListViewFragment(RT_IF_YOU_LIKE);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
