package com.cleanarchitecture

import android.app.Application
import com.cleanarchitecture.data.source.remote.settings.Setting
import com.cleanarchitecture.di.businessModule
import com.cleanarchitecture.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StartClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Setting.BASE_URL =""
        startKoin {
            androidContext(this@StartClass)
            modules(loginModule, businessModule)
        }
    }
}