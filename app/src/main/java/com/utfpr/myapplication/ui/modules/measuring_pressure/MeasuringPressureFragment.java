package com.utfpr.myapplication.ui.modules.measuring_pressure;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentTutorialItemBinding;
import com.utfpr.myapplication.ui.common.BaseFragment;

public class MeasuringPressureFragment extends BaseFragment<MeasuringPressureViewModel, FragmentTutorialItemBinding> {

    public static MeasuringPressureFragment newInstance(){
        return new MeasuringPressureFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public MeasuringPressureViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(MeasuringPressureViewModel.class);
    }

    @Override
    public String getFragmentTag() {
        return MeasuringPressureFragment.class.getSimpleName();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_measuring_pressure;
    }
}
