package com.mobile.musicapp

import android.app.Application

class MusicAppApplication : Application() {
    companion object {
        var INSTANCE: MusicAppApplication? = null
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }
}