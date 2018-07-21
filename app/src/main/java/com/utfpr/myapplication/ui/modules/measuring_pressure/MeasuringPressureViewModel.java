package com.utfpr.myapplication.ui.modules.measuring_pressure;

import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.livedata_resources.SingleLiveEvent;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.ui.common.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MeasuringPressureViewModel extends BaseViewModel {

    SingleLiveEvent<String> createdHistoryIdLiveData = new SingleLiveEvent<>();

    @Inject
    public MeasuringPressureViewModel(){

    }

    public SingleLiveEvent<String> getCreatedHistoryIdLiveData() {
        return createdHistoryIdLiveData;
    }

    public void createHistory(String userId, History history){
        showLoading();

        addDisposable(FirebaseUserManager.createHistory(userId, history)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String historyId) {
                        hideLoading();
                        createdHistoryIdLiveData.setValue(historyId);
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
