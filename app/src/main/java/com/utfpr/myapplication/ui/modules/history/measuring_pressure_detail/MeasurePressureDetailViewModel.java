package com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.ui.common.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MeasurePressureDetailViewModel extends BaseViewModel {

    private MutableLiveData<History> historyMutableLiveData = new MutableLiveData<>();

    @Inject
    public MeasurePressureDetailViewModel() {
    }

    public LiveData<History> getHistoryMutableLiveData() {
        return historyMutableLiveData;
    }

    public void loadHistory(String userId, String historyId) {
        showLoading();

        addDisposable(FirebaseUserManager.getUserHistory(userId, historyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<History>() {
                    @Override
                    public void onNext(History history) {
                        hideLoading();
                        historyMutableLiveData.setValue(history);
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
