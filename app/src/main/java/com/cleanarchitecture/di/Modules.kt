package com.cleanarchitecture.di

import com.cleanarchitecture.data.repository.LocalRepositoryImpl
import com.cleanarchitecture.data.repository.RemoteRepositoryImpl
import com.cleanarchitecture.data.repository.SharedPrefRepositoryImpl
import com.cleanarchitecture.data.source.local.mapper.BusinessLocalMapper
import com.cleanarchitecture.data.source.local.mapper.LoginLocalMapper
import com.cleanarchitecture.domain.interactor.*
import com.cleanarchitecture.domain.repository.LocalRepository
import com.cleanarchitecture.domain.repository.RemoteRepository
import com.cleanarchitecture.domain.repository.SharedPrefRepository
import com.cleanarchitecture.ui.fragment.business.viewmodel.BusinessViewModel
import com.cleanarchitecture.ui.fragment.businessDetails.viewModel.BusinessDetailsViewModel
import com.cleanarchitecture.ui.fragment.login.viewmodel.LoginViewModel
import com.cleanarchitecture.ui.fragment.signup.viewmodel.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
        viewModel{BusinessDetailsViewModel()}
        viewModel{ SignUpViewModel(get()) }
        viewModel { BusinessViewModel(get(),get(),get()) }
        viewModel { LoginViewModel(get(),get(),get(),get(),get()) }
}
