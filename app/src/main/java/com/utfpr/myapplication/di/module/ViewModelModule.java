package com.utfpr.myapplication.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.utfpr.myapplication.di.ViewModelKey;
import com.utfpr.myapplication.ui.MainViewModel;
import com.utfpr.myapplication.ui.common.ViewModelProviderFactory;
import com.utfpr.myapplication.ui.modules.foot_scanner.FootScannerViewModel;
import com.utfpr.myapplication.ui.modules.history.HistoryViewModel;
import com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail.MeasurePressureDetailViewModel;
import com.utfpr.myapplication.ui.modules.login.LoginViewModel;
import com.utfpr.myapplication.ui.modules.main.MainFragmentViewModel;
import com.utfpr.myapplication.ui.modules.measuring_pressure.MeasuringPressureViewModel;
import com.utfpr.myapplication.ui.modules.tutorial.TutorialItemViewModel;
import com.utfpr.myapplication.ui.modules.tutorial.TutorialViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

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

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel.class)
    abstract ViewModel bindMainFragmentViewModel(MainFragmentViewModel var1);

    @Binds
    @IntoMap
    @ViewModelKey(FootScannerViewModel.class)
    abstract ViewModel bindFootScannerViewModel(FootScannerViewModel var1);

    @Binds
    @IntoMap
    @ViewModelKey(MeasuringPressureViewModel.class)
    abstract ViewModel bindMeasuringPressureViewModel(MeasuringPressureViewModel var1);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryViewModel(HistoryViewModel var1);

    @Binds
    @IntoMap
    @ViewModelKey(MeasurePressureDetailViewModel.class)
    abstract ViewModel bindMeasurePressureDetailViewModel(MeasurePressureDetailViewModel var1);
}
