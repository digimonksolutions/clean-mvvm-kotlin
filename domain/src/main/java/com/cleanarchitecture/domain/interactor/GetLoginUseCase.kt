package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.response.Response
import com.cleanarchitecture.domain.repository.RemoteRepository

class GetLoginUseCase(private val remoteRepository: RemoteRepository) {

    suspend fun <T>execute(clazz: Class<T>,url: String, hashMap: HashMap<String,String>): Response<T> {
        return remoteRepository.getLogin(clazz,url,hashMap)
    }

}