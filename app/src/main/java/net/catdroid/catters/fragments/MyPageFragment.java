package net.catdroid.catters.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.catdroid.catters.R;

/**
 * Created by anikaido on 2016/05/15.
 */
public class MyPageFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mypage_fragment, container, false);

        setView((ViewPager) view.findViewById(R.id.mypage_view_pager));
        return view;
    }



    private void setView(ViewPager viewPager) {
        FragmentManager fragmentManager = getFragmentManager();
        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(fragmentManager);

        viewPager.setAdapter(homeFragmentPagerAdapter);
    }
}
