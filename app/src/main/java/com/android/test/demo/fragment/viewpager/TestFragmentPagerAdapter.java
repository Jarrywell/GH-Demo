package com.android.test.demo.fragment.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * des:
 * author: libingyan
 * Date: 18-4-20 16:04
 */
public class TestFragmentPagerAdapter extends FragmentPagerAdapter {

    public TestFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
