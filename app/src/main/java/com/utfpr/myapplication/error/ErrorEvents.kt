package com.utfpr.myapplication.error

import com.utfpr.myapplication.error.RxHttpError

/**
 * Created by lispa on 20/12/2017.
 */
object ErrorEvents {

    /**
     * treat http errors
     */
    class HttpError(error: RxHttpError){
        val error: RxHttpError

        init {
            this.error = error
        }
    }

    /**
     * treat generic errors
     */
    class ThrowableError (error: Throwable){
        val error: Throwable

        init {
            this.error = error
        }
    }
}