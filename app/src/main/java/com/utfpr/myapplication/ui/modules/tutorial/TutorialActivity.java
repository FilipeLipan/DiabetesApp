package com.utfpr.myapplication.ui.modules.tutorial;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.ActivityTutorialBinding;
import com.utfpr.myapplication.ui.common.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lispa on 27/03/2018.
 */

public class TutorialActivity extends BaseFragmentActivity<TutorialViewModel, ActivityTutorialBinding> {

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

        ArrayList<TutorialItem> tutorialItems = getIntent().getParcelableArrayListExtra(TUTORIAL_KEY);

        viewPagerAdapter = new TutorialViewPagerAdapter(getSupportFragmentManager(), tutorialItems);
        getDataBind().viewpagerIndicator.setViewPager(getDataBind().viewpager);
        getDataBind().viewpager.setAdapter(viewPagerAdapter);
    }

    @Override
    public TutorialViewModel getViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(TutorialViewModel.class);
    }

    @Override
    public Integer getActivityLayout() {
        return R.layout.activity_tutorial;
    }

    @Override
    public FrameLayout getContainer() {
        return null;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    public void sawTutorial(){
        getViewModel().sawTutorial();
    }

}
