package com.utfpr.myapplication.ui.modules.tutorial;

import android.arch.lifecycle.ViewModel;

import com.utfpr.myapplication.data.local.LocalPreferences;

import javax.inject.Inject;

/**
 * Created by lispa on 27/03/2018.
 */

public class TutorialViewModel extends ViewModel {

    private LocalPreferences localPreferences;

    @Inject
    public TutorialViewModel(LocalPreferences localPreferences){
        this.localPreferences = localPreferences;
    }

    public void sawTutorial(){
        localPreferences.sawTutorial();
    }
}
