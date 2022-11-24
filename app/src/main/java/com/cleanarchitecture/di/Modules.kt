package com.cleanarchitecture.di

import com.cleanarchitecture.data.repository.RemoteRepositoryImpl
import com.cleanarchitecture.domain.interactor.GetBusinessUseCase
import com.cleanarchitecture.domain.interactor.GetLoginUseCase
import com.cleanarchitecture.domain.repository.RemoteRepository
import com.cleanarchitecture.ui.business.viewmodel.BusinessViewModel
import com.cleanarchitecture.ui.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
        factory { GetLoginUseCase(get()) }
        factory<RemoteRepository> { RemoteRepositoryImpl() }
        viewModel { LoginViewModel(get()) }
}

val businessModule = module {
        factory { GetBusinessUseCase(get()) }
        viewModel { BusinessViewModel(get()) }
}