package com.utfpr.myapplication.ui.modules.tutorial;

import android.arch.lifecycle.ViewModel;

import com.utfpr.myapplication.data.local.LocalPreferences;
import com.utfpr.myapplication.ui.common.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by lispa on 30/03/2018.
 */

public class TutorialItemViewModel extends BaseViewModel {

    private LocalPreferences localPreferences;

    @Inject
    public TutorialItemViewModel(LocalPreferences localPreferences){
        this.localPreferences = localPreferences;
    }

    public void sawTutorial(){
        localPreferences.sawTutorial();
    }
}
