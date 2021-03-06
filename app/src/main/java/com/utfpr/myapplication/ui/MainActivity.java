package com.utfpr.myapplication.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.ActivityMainBinding;
import com.utfpr.myapplication.ui.common.BaseFragmentActivity;
import com.utfpr.myapplication.ui.modules.foot_scanner.FootScannerFragment;
import com.utfpr.myapplication.ui.modules.history.HistoryFragment;
import com.utfpr.myapplication.ui.modules.main.FragmentMain;
import com.utfpr.myapplication.ui.modules.measuring_pressure.MeasuringPressureFragment;

public class MainActivity extends BaseFragmentActivity<MainViewModel, ActivityMainBinding> {

    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void launchAndClearTop(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public MainViewModel getViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(MainViewModel.class);
    }

    @Override
    public Integer getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    public FrameLayout getContainer() {
        return getDataBind().container;
    }

    @Override
    protected Toolbar getToolbar() {
        return getDataBind().appBar.toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            replaceFragment(FragmentMain.newInstance());
        }

        getDataBind().navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(FragmentMain.newInstance());
                    return true;
                case R.id.navigation_foot_scanner:
                    replaceFragment(FootScannerFragment.newInstance());
                    return true;
                case R.id.navigation_measuring_pressure:
                    replaceFragment(MeasuringPressureFragment.newInstance());
                    return true;
                case R.id.navigation_history:
                    replaceFragment(HistoryFragment.newInstance());
                    return true;
            }

            return false;
        });
    }
}
