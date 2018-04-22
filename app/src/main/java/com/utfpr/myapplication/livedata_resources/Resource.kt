package com.utfpr.myapplication.livedata_resources

import com.utfpr.myapplication.livedata_resources.Status.ERROR
import com.utfpr.myapplication.livedata_resources.Status.LOADING
import com.utfpr.myapplication.livedata_resources.Status.SUCCESS

/**
 * Created by lispa on 15/12/2017.
 */

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
