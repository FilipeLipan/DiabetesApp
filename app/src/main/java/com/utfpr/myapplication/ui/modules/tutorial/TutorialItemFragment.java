package com.utfpr.myapplication.ui.modules.tutorial;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.databinding.FragmentTutorialItemBinding;
import com.utfpr.myapplication.ui.MainActivity;
import com.utfpr.myapplication.ui.common.BaseFragment;

/**
 * Created by lispa on 30/03/2018.
 */

public class TutorialItemFragment extends BaseFragment<TutorialItemViewModel, FragmentTutorialItemBinding> {

    private static final String ITEM_KEY = "tutorial-item-key";

    private TutorialItem tutorialItem;

    public static TutorialItemFragment newInstance(TutorialItem tutorialItem){
        TutorialItemFragment tutorialItemFragment = new TutorialItemFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ITEM_KEY, tutorialItem);

        tutorialItemFragment.setArguments(bundle);

        return tutorialItemFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tutorialItem = getArguments().getParcelable(ITEM_KEY);

        getDataBind().tutorialDescriptionTextview.setText(tutorialItem.getDescription());
        getDataBind().tutorialTitleTextview.setText(tutorialItem.getTitle());

        Glide.with(getContext())
                .load(tutorialItem.getIconUrl())
                .into(getDataBind().tutorialImageview);

        if(tutorialItem.isLastItem()){
            getDataBind().skipTutorialButton.setText(R.string.let_get_started);
        }

        getDataBind().skipTutorialButton.setOnClickListener(view1 -> {
            getViewModel().sawTutorial();
            MainActivity.launchAndClearTop(getContext());
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public TutorialItemViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(TutorialItemViewModel.class);
    }

    @Override
    public String getFragmentTag() {
        return TutorialItemFragment.class.getSimpleName();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_tutorial_item;
    }
}
