package com.utfpr.myapplication.ui.modules.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentMainBinding;
import com.utfpr.myapplication.ui.MainViewModel;
import com.utfpr.myapplication.ui.common.BaseFragment;
import com.utfpr.myapplication.ui.modules.login.LoginActivity;

public class FragmentMain extends BaseFragment<MainFragmentViewModel, FragmentMainBinding> {

    public static FragmentMain newInstance(){
        return new FragmentMain();
    }

    @Override
    public MainFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(MainFragmentViewModel.class);
    }

    @Override
    public String getFragmentTag() {
        return "FragmentMain";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDataBind().logoutButton.setOnClickListener( v ->{
            FirebaseAuth.getInstance().signOut();
            LoginActivity.launchAndClearTop(getContext());
        });
    }
}