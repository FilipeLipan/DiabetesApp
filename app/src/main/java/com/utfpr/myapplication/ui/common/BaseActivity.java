package com.utfpr.myapplication.ui.common;

/**
 * Created by lispa on 24/03/2018.
 */

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.utfpr.myapplication.di.ActivityInjectable;

import javax.inject.Inject;

public abstract class BaseActivity<V extends ViewModel, B extends ViewDataBinding> extends AppCompatActivity implements ActivityInjectable {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;


    public abstract V getViewModel();
    public abstract Integer getActivityLayout();

    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getActivityLayout());

    }

    public B getDataBind() {
        return binding;
    }

    public ViewModelProvider.Factory getViewModelFactory() {
        return this.mViewModelFactory;
    }

}