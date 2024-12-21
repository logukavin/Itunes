package com.sample.itunes.application

import android.app.Application
import com.sample.itunes.preferences.AppPreference
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AppController : Application() {

    private var appPreference: AppPreference? = null

    companion object {
        var instance: AppController? = null

        @JvmName("getInstance1")
        @Synchronized
        fun getInstance(): AppController? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appPreference = AppPreference(this)
    }

}