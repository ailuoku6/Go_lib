package com.ailuoku6.golib.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MyBoFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments ;
    private List<String> titles ;

    public MyBoFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}