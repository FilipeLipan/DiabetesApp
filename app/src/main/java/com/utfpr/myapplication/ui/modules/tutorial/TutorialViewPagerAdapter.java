package com.utfpr.myapplication.ui.modules.tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lispa on 30/03/2018.
 */

public class TutorialViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<TutorialItemFragment> fragments = new ArrayList<>();

    public TutorialViewPagerAdapter(FragmentManager fm, ArrayList<TutorialItem> items) {
        super(fm);

        TutorialItem lastItem = items.get(items.size() - 1);

        lastItem.setAsLastItem();

        for (TutorialItem tutorialItem: items) {
            fragments.add(TutorialItemFragment.newInstance(tutorialItem));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
