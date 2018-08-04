package com.utfpr.myapplication.ui.common;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by lispa on 25/03/2018.
 */

public abstract class BaseFragmentActivity<V extends BaseViewModel, B extends ViewDataBinding> extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    protected DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected B binding;
    public abstract V getViewModel();
    public abstract Integer getActivityLayout();
    public abstract FrameLayout getContainer();
    protected abstract Toolbar getToolbar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getActivityLayout());

        if(getActivityLayout() != null) {
            if(getToolbar() != null) {
                setSupportActionBar(getToolbar());
                getToolbar().setTitleTextColor(getResources().getColor(android.R.color.white));
            }
        }

        observeViewModel();
    }

    public void setTitle(@NonNull String title) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void setDisplayHomeAsUpEnabled() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void replaceFragment(@NonNull Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(fragment instanceof BaseFragment){
            fragmentTransaction.replace(getContainer().getId(), fragment, ((BaseFragment) fragment).getFragmentTag());
        }else {
            fragmentTransaction.replace(getContainer().getId(), fragment,  fragment.getClass().getSimpleName());
        }

        fragmentTransaction.commit();
    }


    public void addFragment(@NonNull Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(fragment instanceof BaseFragment){
            fragmentTransaction.add(getContainer().getId(), fragment, ((BaseFragment) fragment).getFragmentTag());
        }else {
            fragmentTransaction.add(getContainer().getId(), fragment,  fragment.getClass().getSimpleName());
        }

        fragmentTransaction.commit();
    }

    public void replaceAndBackStackFragment(@NonNull Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(fragment instanceof BaseFragment){
            fragmentTransaction.replace(getContainer().getId(), fragment, ((BaseFragment) fragment).getFragmentTag());
            fragmentTransaction.addToBackStack(((BaseFragment) fragment).getFragmentTag());
        }else {
            fragmentTransaction.replace(getContainer().getId(), fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        fragmentTransaction.commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    public B getDataBind() {
        return binding;
    }

    public ViewModelProvider.Factory getViewModelFactory() {
        return this.viewModelFactory;
    }

    private void observeViewModel(){
        getViewModel().getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });

        getViewModel().getError().observe(this , error -> {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });
    }

    public void showLoading(){

    }

    public void hideLoading(){

    }

}
