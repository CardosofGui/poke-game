package com.example.pokegame

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.pokegame.framework.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
        }

        AppModules.loadModules()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        baseContext.cacheDir.deleteRecursively()
    }
}