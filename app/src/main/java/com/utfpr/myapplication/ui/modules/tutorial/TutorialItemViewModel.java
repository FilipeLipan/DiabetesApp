package com.utfpr.myapplication.ui.modules.tutorial;

import android.arch.lifecycle.ViewModel;

import com.utfpr.myapplication.data.local.LocalPreferences;

import javax.inject.Inject;

/**
 * Created by lispa on 30/03/2018.
 */

public class TutorialItemViewModel extends ViewModel {

    private LocalPreferences localPreferences;

    @Inject
    public TutorialItemViewModel(LocalPreferences localPreferences){
        this.localPreferences = localPreferences;
    }

    public void sawTutorial(){
        localPreferences.sawTutorial();
    }
}
