package com.utfpr.myapplication.ui.modules.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.utfpr.myapplication.data.FirebaseTutorialManager;
import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.data.UserNotFoundException;
import com.utfpr.myapplication.data.local.LocalPreferences;
import com.utfpr.myapplication.data.local.UserPreferences;
import com.utfpr.myapplication.models.User;
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

public class LoginViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<TutorialItem>> tutorialItemLivedata = new MutableLiveData<>();
    private MutableLiveData<Boolean> goToMainActivityLiveData = new MutableLiveData<Boolean>();

    private LocalPreferences localPreferences;
    private UserPreferences userPreferences;

    @Inject
    public LoginViewModel(LocalPreferences localPreferences, UserPreferences userPreferences){
        this.userPreferences = userPreferences;
        this.localPreferences = localPreferences;
    }

    public LiveData<List<TutorialItem>> getTutorialItemLivedata() {
        return tutorialItemLivedata;
    }

    public MutableLiveData<Boolean> getGoToMainActivityLiveData() {
        return goToMainActivityLiveData;
    }

    private void loadTutorial(){
        compositeDisposable.add(
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
                }));
    }


    public void loadUser(String userId, User user){
        compositeDisposable.add(
                FirebaseUserManager.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        if(localPreferences.didSawTutorial()){
                            goToMainActivityLiveData.setValue(true);
                        }else {
                            loadTutorial();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof UserNotFoundException){
                            createUser(userId, user);
                        }
                    }

                    @Override
                    public void onComplete() { }
                }));
    }

    private void createUser(String userId, User user){
        FirebaseUserManager.createUser(userId, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        loadUser(userId, user);
                    }

                    @Override
                    public void onError(Throwable e) { }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
