package com.utfpr.myapplication.di.module;

import com.utfpr.myapplication.modules.tutorial.TutorialItemFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by lispa on 31/03/2018.
 */

@Module
public abstract class FragmentBindingModule {

//    @ContributesAndroidInjector
//    abstract CardsFragment contributeCardsFragment();
//
//    @ContributesAndroidInjector
//    abstract InvoicesFragment contributeInvoiceFragment();
//
//    @ContributesAndroidInjector
//    abstract PaymentFragment contributePaymentFragment();

    @ContributesAndroidInjector
    abstract TutorialItemFragment contributeTutorialItemFragment();
}

