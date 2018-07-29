package com.utfpr.myapplication.ui.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lispa on 10/06/2018.
 */

public class BaseViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public BaseViewModel(){
        loading.setValue(false);

    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    protected void showLoading(){
        loading.setValue(true);
    }

    protected void hideLoading(){
        loading.setValue(false);
    }

    protected void addDisposable(Disposable disposable){
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
