package com.utfpr.myapplication.ui.modules.measuring_pressure;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentMeasuringPressureBinding;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.ui.common.BaseFragment;
import com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail.MeasurePressureDetailActivity;

import java.util.Date;

public class MeasuringPressureFragment extends BaseFragment<MeasuringPressureViewModel, FragmentMeasuringPressureBinding> {

    public static MeasuringPressureFragment newInstance(){
        return new MeasuringPressureFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().setTitle(getString(R.string.measuring_pressure));


        getDataBind().teste.setOnClickListener(view1 -> {
            getViewModel().createHistory(FirebaseAuth.getInstance().getUid(), new History().setResult("teste").setCreatedAt(new Date()));
        });

        observeLiveData();
    }

    private void observeLiveData() {
        getViewModel().getCreatedHistoryIdLiveData().observe(this, historyId -> {
            if(historyId != null){
                MeasurePressureDetailActivity.launchWithId(getContext(), historyId);
            }
        });
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
