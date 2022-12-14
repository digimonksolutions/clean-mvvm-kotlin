package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.repository.LocalRepository
import com.cleanarchitecture.domain.model.login.LoginModel

class GetUserDBUseCase(private val localRepository: LocalRepository) {

    suspend fun execute(email:String,password: String): LoginModel?{
        return localRepository.getUser(email,password)
    }
}