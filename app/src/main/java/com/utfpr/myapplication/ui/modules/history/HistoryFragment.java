package com.utfpr.myapplication.ui.modules.history;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentExamHistoryBinding;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.ui.common.BaseFragment;
import com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail.MeasurePressureDetailActivity;
import com.utfpr.myapplication.utils.StringUtils;
import com.utfpr.myapplication.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends BaseFragment<HistoryViewModel, FragmentExamHistoryBinding> implements SwipeRefreshLayout.OnRefreshListener{

    private HistoryAdapter mAdapter;

    public static HistoryFragment newInstance(){
        return new HistoryFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().setTitle(getString(R.string.history));

        initAdapter();
        observeLiveData();

        getDataBind().swipeToRefresh.setOnRefreshListener(this);
    }

    private void observeLiveData() {
        getViewModel().getHistoryMutableLiveData().observe(this, allHistory -> {
            if(allHistory != null){
                initViews(allHistory);
                mAdapter.setEmptyView(ViewUtils.inflateView(getActivity(), R.layout.empty_list_view));
            }
        });
    }

    private void initViews(List<History> allHistory) {
        mAdapter.setNewData(allHistory);
    }

    private void initAdapter(){
        mAdapter = new HistoryAdapter(getContext(), new ArrayList<>());
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            History history = (History) adapter.getData().get(position);
            if(history.getType().equalsIgnoreCase(StringUtils.HEART_BEAT_TYPE)) {
                MeasurePressureDetailActivity.launchWithHistory(getContext(),history);
            }
        });
        getDataBind().historyRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {
        getDataBind().swipeToRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        getDataBind().swipeToRefresh.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        getViewModel().getAllUserHistory(FirebaseAuth.getInstance().getUid());
    }
}
