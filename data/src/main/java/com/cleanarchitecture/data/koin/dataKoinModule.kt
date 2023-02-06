package com.cleanarchitecture.data.koin

import com.cleanarchitecture.data.repository.LocalRepositoryImpl
import com.cleanarchitecture.data.repository.RemoteRepositoryImpl
import com.cleanarchitecture.data.repository.SharedPrefRepositoryImpl
import com.cleanarchitecture.data.source.local.mapper.BusinessLocalMapper
import com.cleanarchitecture.data.source.local.mapper.LoginLocalMapper
import com.cleanarchitecture.domain.repository.LocalRepository
import com.cleanarchitecture.domain.repository.RemoteRepository
import com.cleanarchitecture.domain.repository.SharedPrefRepository
import org.koin.dsl.module

val dataKoinModule = module {

    single { BusinessLocalMapper() }
    single { LoginLocalMapper() }
    factory<RemoteRepository> { RemoteRepositoryImpl() }
    factory<LocalRepository> { LocalRepositoryImpl(get(),get(),get()) }
    factory<SharedPrefRepository> { SharedPrefRepositoryImpl(get()) }
}