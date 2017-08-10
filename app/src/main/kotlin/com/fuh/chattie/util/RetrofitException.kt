package com.fuh.chattie.util

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException


class RetrofitException private constructor(override val message: String?,
                                            override val cause: Throwable?,
                                            /** The request URL which produced the error. */
                        val url: String?,
                                            /** Response object containing status code, headers, body, etc. */
                        val response: Response<*>?,
                                            /** The event Kind which triggered this error. */
                        val kind: Kind?,
                                            /** The Retrofit this request was executed on */
                        val retrofit: Retrofit?):
        RuntimeException(message, cause) {

    companion object {
        fun httpError(url: String?, response: Response<*>?, retrofit: Retrofit?): RetrofitException {
            val message = response?.let { it.code().toString() + " " + it.message() }
            return RetrofitException(message, null, url, response, Kind.HTTP, retrofit)
        }

        fun networkError(exception: IOException?): RetrofitException {
            return RetrofitException(exception?.message, exception, null, null, Kind.NETWORK, null)
        }

        fun unexpectedError(exception: Throwable?): RetrofitException {
            return RetrofitException(exception?.message, exception, null, null, Kind.UNEXPECTED, null)
        }
    }

    /**
     * HTTP response body converted to specified `type`. `null` if there is no
     * response.
     */
    fun <T> getErrorBodyAs(type: Class<T>): T {
        val converter = retrofit?.responseBodyConverter<T>(type, arrayOf(object : Annotation{}))
        val body = response?.errorBody()

        return try {
            body.let { converter!!.convert(it) }
        } catch (ioe: IOException) {
            throw IllegalArgumentException(ioe)
        } catch (npe: NullPointerException) {
            throw IllegalArgumentException(npe)
        }

    }

    /** Identifies the event kind which triggered a [RetrofitException].  */
    enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,
        /** A non-200 HTTP status code was received from the server.  */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}