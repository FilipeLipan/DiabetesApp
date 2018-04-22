package com.utfpr.myapplication.common;

import android.support.v4.app.Fragment;

/**
 * Created by lispa on 25/03/2018.
 */

public interface IBaseFragmentActivityListener {
    void setTitle(String fragment);

    void setDisplayHomeAsUpEnabled();

    void replaceFragment(Fragment fragment);

    void addFragment(Fragment fragment);

    void replaceAndBackStackFragment(Fragment fragment);
}
