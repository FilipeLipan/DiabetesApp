package com.utfpr.myapplication.ui.modules.history;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.ui.common.BaseFragment;
import com.utfpr.myapplication.databinding.FragmentExamHistoryBinding;

import java.util.ArrayList;

public class HistoryFragment extends BaseFragment<HistoryViewModel, FragmentExamHistoryBinding> {

    public static HistoryFragment newInstance(){
        return new HistoryFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().setTitle(getString(R.string.history));

        ArrayList<HistoryItem> items = new ArrayList<>();

        for (int i = 0; i < 10 ; i++) {
            items.add(new HistoryItem());
        }

        getDataBind().historyRecyclerview.setAdapter(new HistoryAdapter(items));

    }

    @Override
    public HistoryViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(HistoryViewModel.class);
    }

    @Override
    public String getFragmentTag() {
        return "HistoryFragment";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_exam_history;
    }
}
