package com.mygomii.musicplayer

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MusicPlayerApp : Application() {
    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()

        app = this

        startKoin {
            androidContext(this@MusicPlayerApp)
        }
    }
}