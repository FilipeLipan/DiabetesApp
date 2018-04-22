package com.utfpr.myapplication.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.utfpr.myapplication.LoginViewModel;
import com.utfpr.myapplication.MainViewModel;
import com.utfpr.myapplication.common.ViewModelProviderFactory;
import com.utfpr.myapplication.di.ViewModelKey;
import com.utfpr.myapplication.modules.tutorial.TutorialItemViewModel;
import com.utfpr.myapplication.modules.tutorial.TutorialViewModel;

/**
 * Created by lispa on 31/03/2018.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel var1);


    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel.class)
    abstract ViewModel bindTutorialViewModel(TutorialViewModel var1);

    @Binds
    @IntoMap
    @ViewModelKey(TutorialItemViewModel.class)
    abstract ViewModel bindTutorialItemViewModel(TutorialItemViewModel var1);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel var1);
}
