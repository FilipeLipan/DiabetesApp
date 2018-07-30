package com.utfpr.myapplication.ui.modules.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.utfpr.myapplication.data.FirebaseTutorialManager;
import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.data.UserNotFoundException;
import com.utfpr.myapplication.data.local.LocalPreferences;
import com.utfpr.myapplication.data.local.UserPreferences;
import com.utfpr.myapplication.models.User;
import com.utfpr.myapplication.ui.common.BaseViewModel;
import com.utfpr.myapplication.ui.modules.tutorial.TutorialItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lispa on 25/03/2018.
 */

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<List<TutorialItem>> tutorialItemLivedata = new MutableLiveData<>();
    private MutableLiveData<Boolean> goToMainActivityLiveData = new MutableLiveData<Boolean>();
    private final FirebaseTutorialManager firebaseTutorialManager;

    private LocalPreferences mLocalPreferences;
    private UserPreferences mUserPreferences;
    private final FirebaseUserManager firebaseUserManager;

    @Inject
    public LoginViewModel(LocalPreferences localPreferences, UserPreferences userPreferences, FirebaseTutorialManager firebaseTutorialManager, FirebaseUserManager firebaseUserManager){
        this.mUserPreferences = userPreferences;
        this.mLocalPreferences = localPreferences;
        this.firebaseTutorialManager = firebaseTutorialManager;
        this.firebaseUserManager = firebaseUserManager;
    }

    public LiveData<List<TutorialItem>> getTutorialItemLivedata() {
        return tutorialItemLivedata;
    }

    public MutableLiveData<Boolean> getGoToMainActivityLiveData() {
        return goToMainActivityLiveData;
    }

    private void loadTutorial(){
        showLoading();
        addDisposable(
                firebaseTutorialManager.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<TutorialItem>>() {
                            @Override
                            public void onNext(List<TutorialItem> tutorialItems) {
                                hideLoading();
                                tutorialItemLivedata.setValue(tutorialItems);
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideLoading();
                            }

                            @Override
                            public void onComplete() {
                            }
                        }));
    }


    public void loadUser(String userId, User user){
        showLoading();
        addDisposable(
                firebaseUserManager.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        mUserPreferences.saveUser(userId, user);
                        if(mLocalPreferences.didSawTutorial()){
                            goToMainActivityLiveData.setValue(true);
                        }else {
                            loadTutorial();
                        }
                        hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        if(e instanceof UserNotFoundException){
                            createUser(userId, user);
                        }
                    }

                    @Override
                    public void onComplete() { }
                }));
    }

    private void createUser(String userId, User user){
        showLoading();
        firebaseUserManager.createUser(userId, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                        loadUser(userId, user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }
                });
    }
}
