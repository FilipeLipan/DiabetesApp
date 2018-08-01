package com.utfpr.myapplication.app;

import android.app.Activity;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.utfpr.myapplication.di.AppInjector;
import com.utfpr.myapplication.di.component.AppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;

public class App extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityAndroidInjector;
    private AppComponent appComponent;

    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Fabric.with(this, new Crashlytics());
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
