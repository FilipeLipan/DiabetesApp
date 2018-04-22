package com.utfpr.myapplication.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.support.v7.app.AppCompatDelegate;

import com.utfpr.myapplication.di.AppInjector;
import com.utfpr.myapplication.di.component.AppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import javax.inject.Inject;

public class App extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityAndroidInjector;
    private AppComponent appComponent;

    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.appComponent = AppInjector.init(this);
    }

    public AppComponent getAppComponent() {
        return this.appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return this.dispatchingActivityAndroidInjector;
    }

}
