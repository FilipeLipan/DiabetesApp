package com.utfpr.myapplication.ui.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by lispa on 10/06/2018.
 */

public class BaseViewModel extends ViewModel {

    MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public BaseViewModel(){
        loading.setValue(false);

    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void showLoading(){
        loading.setValue(true);
    }

    public void hideLoading(){
        loading.setValue(false);
    }
}
