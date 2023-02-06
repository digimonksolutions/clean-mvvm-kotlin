package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.repository.SharedPrefRepository

class SaveUserLoginUseCase(private val sharedPrefRepository: SharedPrefRepository) {

    fun execute(boolean: Boolean){
        sharedPrefRepository.setLogin(boolean)
    }

}
