package com.utfpr.myapplication.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utfpr.myapplication.di.Injectable;

import javax.inject.Inject;

/**
 * Created by lispa on 25/03/2018.
 */

public abstract class BaseFragment<V extends ViewModel, B extends ViewDataBinding> extends Fragment implements Injectable{

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    public abstract V getViewModel();
    public abstract String getFragmentTag();
    @LayoutRes
    public abstract int getFragmentLayout();

    protected V mViewModel;

    protected B dataBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        B binding = DataBindingUtil.inflate(inflater, getFragmentLayout(),  container, false);

        dataBind = binding;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
    }

    public B getDataBind() {
        return dataBind;
    }

    public BaseFragmentActivity getBaseActivity(){
        return (BaseFragmentActivity) getActivity();
    }
}