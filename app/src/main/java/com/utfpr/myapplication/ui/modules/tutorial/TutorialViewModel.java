package com.utfpr.myapplication.ui.modules.tutorial;

import com.utfpr.myapplication.data.local.LocalPreferences;
import com.utfpr.myapplication.ui.common.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by lispa on 27/03/2018.
 */

public class TutorialViewModel extends BaseViewModel {

    private LocalPreferences localPreferences;

    @Inject
    public TutorialViewModel(LocalPreferences localPreferences){
        this.localPreferences = localPreferences;
    }

    public void sawTutorial(){
        localPreferences.sawTutorial();
    }
}
