package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.repository.RemoteRepository
import com.cleanarchitecture.domain.response.Response

class GetBusinessUseCase(private val remoteRepository: RemoteRepository) {

    suspend fun <T> execute(classDataObject:Class<T>,url:String): Response<T> {
        return remoteRepository.getBusiness(classDataObject,url)
    }

}