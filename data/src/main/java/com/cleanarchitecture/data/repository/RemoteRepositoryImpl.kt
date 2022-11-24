package com.cleanarchitecture.data.repository

import com.cleanarchitecture.data.source.remote.api.RestClient
import com.cleanarchitecture.domain.response.Response
import com.cleanarchitecture.domain.repository.RemoteRepository

class RemoteRepositoryImpl(): RemoteRepository {
    val restClient = RestClient()

    override suspend fun <T> getLogin(
        classDataObject: Class<T>,
        url: String,
        hashMap: HashMap<String, String>
    ): Response<T> {
        return restClient.post(classDataObject,url,hashMap)
    }

    override suspend fun <T> getBusiness(classDataObject: Class<T>, url: String): Response<T> {
        return restClient.get(classDataObject,url)
    }
}