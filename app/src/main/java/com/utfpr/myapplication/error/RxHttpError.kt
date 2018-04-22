package com.utfpr.myapplication.error

import com.google.gson.Gson
import java.io.IOException
import java.util.HashMap

import retrofit2.HttpException


class RxHttpError private constructor() {

    var detail: String? = null

    var exception: Throwable? = null

    var errorCode: Int = 0
    var errorObject: Any? = null
    var errorMessage: String? = null

    private inner class Non400 {
        var message: String? = null
        var code: Int = 0
    }

    companion object {

        const val NO_CONNECTIVITY_CODE = 1000
        const val UNKNOWNHOST_CODE = 1002
        const val SOCKETTIMEOUT_CODE = 1003

        /**
         * Traduz uma exceção para um RxHttpError

         * @see RxHttpError


         * @param e exceção
         * *
         * @return RxHttpError
         * *
         * @throws IOException
         */
        @Throws(IOException::class)
        fun parseError(e: Throwable): RxHttpError? {

            val typesByCode = HashMap<Int, Class<*>>()
//            typesByCode.put(HttpURLConnection.HTTP_BAD_REQUEST, FormError::class.java)

            return parseError(e, Gson(), typesByCode)
        }

        /**
         * Traduz uma exceção para um RxHttpError

         * @see RxHttpError


         * @param e
         * *
         * @param parser
         * *
         * @param typesByCode Classes que serão traduzidas de acordo com o StatusCode retornado da requisição
         * *
         * @return
         * *
         * @throws IOException
         */
        @Throws(IOException::class)
        fun parseError(e: Throwable, parser: Gson, typesByCode: HashMap<Int, Class<*>>): RxHttpError? {
            val error = RxHttpError()
            error.exception = e

            if (e is java.net.UnknownHostException) {
                error.errorCode = UNKNOWNHOST_CODE
            } else if (e is java.net.ConnectException) {
                error.errorCode = NO_CONNECTIVITY_CODE
            } else if (e is java.net.SocketTimeoutException) {
                error.errorCode = SOCKETTIMEOUT_CODE
            } else if (e is HttpException) {
                parseKnownError(error, e, parser, typesByCode)
            } else {
                return null
            }


            return error
        }

        @Throws(IOException::class)
        private fun parseKnownError(error: RxHttpError, httpError: HttpException, parser: Gson, typesByCode: HashMap<Int, Class<*>>) {
            //TODO --improve change this parser for moviedb
            error.errorCode = httpError.code()
            error.errorMessage = httpError.message()

            try {
                val non400 = parser.fromJson(httpError.response().errorBody()!!.string(), Non400::class.java)
                error.detail = non400.message
            } catch (ex: Exception) {
                ex.printStackTrace()
                error.detail = ""
            }
        }
    }
}