package com.utfpr.myapplication.ui.modules.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.utfpr.myapplication.data.FirebaseNewsManager;
import com.utfpr.myapplication.models.News;
import com.utfpr.myapplication.ui.common.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lispa on 19/04/2018.
 */

public class MainFragmentViewModel extends BaseViewModel {

    private MutableLiveData<List<News>> newsMutableLiveData = new MutableLiveData<>();
    private final FirebaseNewsManager firebaseNewsManager;

    @Inject
    public MainFragmentViewModel(FirebaseNewsManager firebaseNewsManager){
        this.firebaseNewsManager = firebaseNewsManager;
        getAllNews();
    }

    public LiveData<List<News>> getNewsMutableLiveData() {
        return newsMutableLiveData;
    }

    private void getAllNews(){
        showLoading();

        addDisposable(
                firebaseNewsManager.getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<News>>() {
                            @Override
                            public void onNext(List<News> news) {
                                hideLoading();
                                newsMutableLiveData.setValue(news);
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideLoading();
                            }

                            @Override
                            public void onComplete() {
                                //ignore
                            }
                        }));
    }
}