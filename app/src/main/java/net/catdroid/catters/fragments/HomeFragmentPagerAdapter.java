package net.catdroid.catters.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
                return new HomeFragment(CAT_APARTMENT);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
