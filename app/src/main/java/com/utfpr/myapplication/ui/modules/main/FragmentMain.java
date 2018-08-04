package com.utfpr.myapplication.ui.modules.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentMainBinding;
import com.utfpr.myapplication.ui.common.BaseFragment;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.models.News;
import com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail.MeasurePressureDetailActivity;
import com.utfpr.myapplication.ui.modules.login.LoginActivity;
import com.utfpr.myapplication.utils.StringUtils;
import com.utfpr.myapplication.utils.ViewUtils;

import java.util.ArrayList;

public class FragmentMain extends BaseFragment<MainFragmentViewModel, FragmentMainBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private NewsAdapter mAdapter;

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

        getBaseActivity().setTitle(getString(R.string.title_home));

        getDataBind().logoutButton.setOnClickListener( v ->{
            FirebaseAuth.getInstance().signOut();
            LoginActivity.launchAndClearTop(getContext());
        });

        initAdapter();

        getDataBind().newsSwipeToRefresh.setOnRefreshListener(this);

        observeLiveData();
    }

    private void observeLiveData() {
        getViewModel().getNewsMutableLiveData().observe(this, allNews -> {
            if(allNews != null){
                mAdapter.setNewData(allNews);
                mAdapter.setEmptyView(ViewUtils.inflateView(getActivity(), R.layout.empty_list_view));
            }
        });
    }

    private void initAdapter(){
        mAdapter = new NewsAdapter(getContext(), new ArrayList<>());
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            History history = (History) adapter.getData().get(position);
            if(history.getType().equalsIgnoreCase(StringUtils.HEART_BEAT_TYPE)) {
                MeasurePressureDetailActivity.launchWithHistory(getContext(),history);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            News news = (News) adapter.getData().get(position);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.getLink()));
            startActivity(i);
        });

        getDataBind().newsRecyclerview.setAdapter(mAdapter);

    }

    @Override
    public void onRefresh() {
        getViewModel().getAllNews();
    }

    @Override
    public void showLoading() {
        getDataBind().newsSwipeToRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        getDataBind().newsSwipeToRefresh.setRefreshing(false);
    }
}
