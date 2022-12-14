package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.repository.LocalRepository
import com.cleanarchitecture.domain.model.login.LoginModel

class CreateUserDbUseCase(private val localRepository: LocalRepository) {
    suspend fun execute(obj: LoginModel){
        localRepository.createUser(obj)
    }
}