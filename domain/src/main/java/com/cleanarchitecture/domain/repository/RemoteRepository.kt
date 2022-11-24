package com.cleanarchitecture.domain.repository

import com.cleanarchitecture.domain.response.Response

interface RemoteRepository {

    suspend fun <T> getLogin(classDataObject: Class<T>, url:String, hashMap: HashMap<String,String>): Response<T>

    suspend fun <T> getBusiness(classDataObject: Class<T>, url:String): Response<T>

}