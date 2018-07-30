package com.utfpr.myapplication.ui.modules.history;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.ui.common.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HistoryViewModel extends BaseViewModel {

    private MutableLiveData<List<History>> historyMutableLiveData = new MutableLiveData<>();

    private final FirebaseUserManager firebaseUserManager;

    @Inject
    HistoryViewModel(FirebaseUserManager firebaseUserManager){
        this.firebaseUserManager = firebaseUserManager;
        getAllUserHistory(FirebaseAuth.getInstance().getUid());
    }

    public LiveData<List<History>> getHistoryMutableLiveData() {
        return historyMutableLiveData;
    }

    public void getAllUserHistory(String userId){
        showLoading();

        addDisposable(firebaseUserManager.getAllUserHistory(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<History>>() {
                    @Override
                    public void onNext(List<History> histories) {
                        hideLoading();
                        historyMutableLiveData.setValue(histories);
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
}
