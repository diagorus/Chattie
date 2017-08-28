package com.fuh.chattie

import android.app.Application
import timber.log.Timber

/**
 * Created by lll on 11.08.2017.
 */
class App : Application() {

    companion object {
        lateinit var appName: String
    }

    override fun onCreate() {
        super.onCreate()

        appName = resources.getString(R.string.app_name)

        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}