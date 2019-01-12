package com.magnae.bcl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_OF_SLIDERS = 5;

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new RecommendationFragment();
            case 1:
                return new PopularityFragment();
            case 2:
                return new MovieFragment();
            case 3:
                return new LectureFragment();
            case 4:
                return new NoticeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_SLIDERS;
    }
}
