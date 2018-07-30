package com.utfpr.myapplication.di.component;

import android.app.Application;

import com.utfpr.myapplication.app.App;
import com.utfpr.myapplication.di.module.ActivityBindingModule;
import com.utfpr.myapplication.di.module.AppModule;
import com.utfpr.myapplication.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by feph on 24/10/2017.
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBindingModule.class, ViewModelModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);

//    void inject(InstanceIDListenerService instanceIDListenerService);

}
