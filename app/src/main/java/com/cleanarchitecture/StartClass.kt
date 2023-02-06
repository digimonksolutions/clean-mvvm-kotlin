package com.cleanarchitecture

import android.app.Application
import com.cleanarchitecture.data.koin.dataKoinModule
import com.cleanarchitecture.data.source.remote.settings.Setting
import com.cleanarchitecture.di.*
import com.cleanarchitecture.domain.koin.domainKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StartClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Setting.BASE_URL ="YOUR API BASE URL HERE"
        startKoin {
            androidContext(this@StartClass)
            modules(appModule, dataKoinModule, domainKoinModule)
        }
    }
}