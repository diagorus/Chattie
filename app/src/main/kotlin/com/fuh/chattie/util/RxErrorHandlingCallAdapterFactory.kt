package com.fuh.chattie.util

import io.reactivex.Observable
import io.reactivex.Scheduler
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.HttpException
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import rx.Observable
import rx.Scheduler
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory private constructor(scheduler: Scheduler? = null): CallAdapter.Factory() {

    val original: RxJava2CallAdapterFactory = scheduler?.let {
        RxJava2CallAdapterFactory.createWithScheduler(it)
    } ?: RxJava2CallAdapterFactory.create()


    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*> {
        return RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit))
    }

    companion object {
        fun create() = RxErrorHandlingCallAdapterFactory()

        fun createWithScheduler(scheduler: Scheduler) = RxErrorHandlingCallAdapterFactory(scheduler)
    }

    private class RxCallAdapterWrapper(val retrofit: Retrofit?, val wrapped: CallAdapter<*>?): CallAdapter<Observable<*>> {

        override fun <R : Any?> adapt(call: Call<R>?): Observable<*>? {
            return (wrapped?.adapt(call) as? Observable<*>)?.onErrorResumeNext{
                Observable.error(asRetrofitException(it))
            }
        }

        override fun responseType(): Type? = wrapped?.responseType()

        private fun asRetrofitException(throwable: Throwable): RetrofitException = when (throwable) {
            // We had non-200 http error
            is HttpException -> with(throwable.response()) {
                RetrofitException.httpError(raw().request().url().toString(), this, retrofit)
            }
            // A network error happened
            is IOException -> RetrofitException.networkError(throwable)
            // We don't know what happened. We need to simply convert to an unknown error
            else -> RetrofitException.unexpectedError(throwable)
        }
    }
}