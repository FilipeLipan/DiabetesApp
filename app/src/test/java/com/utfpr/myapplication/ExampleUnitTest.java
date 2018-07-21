package com.utfpr.myapplication;

import com.utfpr.myapplication.data.FirebaseUserManager;
import com.utfpr.myapplication.models.History;

import org.junit.Test;

import java.util.Date;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void create_history_test(){
        TestObserver<String> subscriber = new TestObserver<>();

        Observable observable = FirebaseUserManager.createHistory("5lcpLADN0LOzsB2bDe6yrPCtgwr1", new History().setCreatedAt(new Date()).setResult("Detectado"));

        observable.subscribeWith(subscriber);

        subscriber.assertComplete();
        subscriber.assertNoErrors();
        assert(!subscriber.values().isEmpty());
    }
}