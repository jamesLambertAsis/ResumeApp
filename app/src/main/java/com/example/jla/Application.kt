package com.example.jla

import android.app.Application
import com.example.jla.di.app
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(app)
        }
    }

}