package com.utfpr.myapplication.modules.tutorial;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.ViewParent;

import com.rd.PageIndicatorView;
import com.utfpr.myapplication.LoginViewModel;
import com.utfpr.myapplication.MainActivity;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lispa on 27/03/2018.
 */

public class TutorialActivity extends BaseActivity {


    private TutorialViewPagerAdapter viewPagerAdapter;

    public static final String TUTORIAL_KEY = "tutorial-key";

    public static void launch(Context context, List<TutorialItem> tutorialItems){
        Intent intent = new Intent(context, TutorialActivity.class);
        intent.putParcelableArrayListExtra(TUTORIAL_KEY,(ArrayList)  tutorialItems);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager viewPager = findViewById(R.id.viewpager);
        PageIndicatorView viewpagerIndicator = findViewById(R.id.viewpager_indicator);

        ArrayList<TutorialItem> tutorialItems = getIntent().getParcelableArrayListExtra(TUTORIAL_KEY);

        viewPagerAdapter = new TutorialViewPagerAdapter(getSupportFragmentManager(), tutorialItems);

        viewpagerIndicator.setViewPager(viewPager);

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public ViewModel getViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(LoginViewModel.class);
    }

    @Override
    public Integer getActivityLayout() {
        return R.layout.activity_tutorial;
    }
}
