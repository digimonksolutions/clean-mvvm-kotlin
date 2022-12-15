package com.cleanarchitecture.di

import com.cleanarchitecture.data.repository.LocalRepositoryImpl
import com.cleanarchitecture.data.repository.RemoteRepositoryImpl
import com.cleanarchitecture.data.source.local.mapper.BusinessLocalMapper
import com.cleanarchitecture.data.source.local.mapper.LoginLocalMapper
import com.cleanarchitecture.domain.interactor.*
import com.cleanarchitecture.domain.repository.LocalRepository
import com.cleanarchitecture.domain.repository.RemoteRepository
import com.cleanarchitecture.ui.fragment.business.viewmodel.BusinessViewModel
import com.cleanarchitecture.ui.fragment.businessDetails.viewModel.BusinessDetailsViewModel
import com.cleanarchitecture.ui.fragment.login.viewmodel.LoginViewModel
import com.cleanarchitecture.ui.fragment.signup.viewmodel.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
        single { BusinessLocalMapper() }
        factory { GetLoginUseCase(get()) }
        factory { GetUserDBUseCase(get()) }
        factory<RemoteRepository> { RemoteRepositoryImpl() }
        factory<LocalRepository> { LocalRepositoryImpl(androidContext(),get(),get()) }
        viewModel { LoginViewModel(get(),get(),get()) }
}

val signUpModule = module {
        single { LoginLocalMapper() }
        factory { CreateUserDbUseCase(get()) }
        viewModel{ SignUpViewModel(get()) }

}

val businessModule = module {
        factory { GetBusinessUseCase(get()) }
        factory { AddBusinessToDBUseCase(get()) }
        factory { GetBusinessFromDBUseCase(get()) }
        viewModel { BusinessViewModel(get(),get(),get()) }
}

val businessDetailsModule = module {
        viewModel{BusinessDetailsViewModel()}
}