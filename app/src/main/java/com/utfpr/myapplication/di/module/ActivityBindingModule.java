package com.utfpr.myapplication.di.module;

import com.utfpr.myapplication.LoginActivity;
import com.utfpr.myapplication.MainActivity;
import com.utfpr.myapplication.modules.tutorial.TutorialActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by lispa on 31/03/2018.
 */

@Module
public abstract class ActivityBindingModule {


    @ContributesAndroidInjector()
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {FragmentBindingModule.class})
    abstract TutorialActivity bindTutorialActivity();

    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();
}
