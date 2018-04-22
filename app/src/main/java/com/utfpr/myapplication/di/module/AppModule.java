package com.utfpr.myapplication.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lispa on 31/03/2018.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

//    @Provides
//    @Singleton
//    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
//        return jobExecutor;
//    }
//
//    @Provides
//    @Singleton
//    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
//        return uiThread;
//    }
}
