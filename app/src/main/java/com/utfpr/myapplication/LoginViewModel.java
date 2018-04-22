package com.utfpr.myapplication;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.utfpr.myapplication.data.FirebaseTutorialManager;
import com.utfpr.myapplication.modules.tutorial.TutorialItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lispa on 25/03/2018.
 */

public class LoginViewModel extends ViewModel {

    private MutableLiveData<List<TutorialItem>> tutorialItemLivedata = new MutableLiveData<>();

    @Inject
    public LoginViewModel(){

    }

    public MutableLiveData<List<TutorialItem>> getTutorialItemLivedata() {
        return tutorialItemLivedata;
    }

    public void loadTutorial(){

        FirebaseTutorialManager.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<TutorialItem>>() {
                    @Override
                    public void onNext(List<TutorialItem> tutorialItems) {
                        tutorialItemLivedata.setValue(tutorialItems);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
