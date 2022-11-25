package com.cleanarchitecture

import android.app.Application
import com.cleanarchitecture.data.source.remote.settings.Setting
import com.cleanarchitecture.di.businessModule
import com.cleanarchitecture.di.loginModule
import com.cleanarchitecture.di.signUpModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StartClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Setting.BASE_URL ="YOUR API BASE URL HERE"
        startKoin {
            androidContext(this@StartClass)
            modules(loginModule, signUpModule,businessModule)
        }
    }
}