package com.fuh.chattie

import android.app.Application
import android.os.SystemClock
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by lll on 11.08.2017.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}