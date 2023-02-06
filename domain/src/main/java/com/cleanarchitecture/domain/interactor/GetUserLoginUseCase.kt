package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.repository.SharedPrefRepository

class GetUserLoginUseCase(private val sharedPrefRepository: SharedPrefRepository) {

    fun execute():Boolean{
        return sharedPrefRepository.getLogin()
    }
}