package com.utfpr.myapplication.error


import java.io.IOException


object IErrorHandlerHelper {

    /**
     * Helper for generic errors
     */
    fun defaultErrorResolver(e: Throwable) {
        try {
            val error = RxHttpError.parseError(e)
            if (error != null) {
                RxErrorEventBus.send(ErrorEvents.HttpError(error))
            } else {
                RxErrorEventBus.send(ErrorEvents.ThrowableError(e))
            }
        } catch (e1: IOException) {
            RxErrorEventBus.send(ErrorEvents.ThrowableError(e))
        }

    }

}