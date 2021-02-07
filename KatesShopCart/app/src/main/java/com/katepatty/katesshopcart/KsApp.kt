package com.katepatty.katesshopcart

import android.app.Application
import timber.log.Timber

class KsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}