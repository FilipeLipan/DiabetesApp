package com.utfpr.myapplication.error

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by lispa on 20/12/2017.
 */
object RxErrorEventBus {
    private val bus = PublishSubject.create<Any>()

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }

    fun <T> toObservable(klazz: Class<T>): Observable<T> {
        return bus.ofType(klazz)
    }
}