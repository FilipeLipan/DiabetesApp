package com.utfpr.myapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.models.History;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void create_history_test() throws InterruptedException {
        TestObserver<String> subscriber = new TestObserver<>();

        FirebaseAuth.getInstance().signInAnonymously();

        Thread.sleep(5000);

        Observable observable = FirebaseUserManager.createHistory("5lcpLADN0LOzsB2bDe6yrPCtgwr1", new History().setCreatedAt(new Date()).setResult("Detectado"));

        observable.subscribeWith(subscriber);

        subscriber.assertNoErrors();
        Log.w("lolll", subscriber.values().get(0));
        assert(!subscriber.values().get(0).isEmpty());
    }
}
