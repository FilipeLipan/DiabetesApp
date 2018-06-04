package com.utfpr.myapplication.di.module;

import com.utfpr.myapplication.ui.modules.foot_scanner.FootScannerFragment;
import com.utfpr.myapplication.ui.modules.main.FragmentMain;
import com.utfpr.myapplication.ui.modules.tutorial.TutorialItemFragment;

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

    @ContributesAndroidInjector
    abstract FootScannerFragment contributeFootScannerFragment();

    @ContributesAndroidInjector
    abstract FragmentMain contributeFragmentMain();
}

