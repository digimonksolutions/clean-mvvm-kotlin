package com.cleanarchitecture.domain.koin

import com.cleanarchitecture.domain.interactor.*
import org.koin.dsl.module

val domainKoinModule = module {
    factory { AddBusinessToDBUseCase(get()) }
    factory { CreateUserDbUseCase(get()) }
    factory { GetBusinessFromDBUseCase(get()) }
    factory { GetBusinessUseCase(get()) }
    factory { GetLoginUseCase(get()) }
    factory { GetUserDBUseCase(get()) }
    factory { GetUserLoginUseCase(get()) }
    factory { SaveUserLoginUseCase(get()) }
}