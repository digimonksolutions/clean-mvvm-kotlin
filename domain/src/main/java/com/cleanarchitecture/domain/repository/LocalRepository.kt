package com.cleanarchitecture.domain.repository

import com.cleanarchitecture.model.business.BusinessModel
import com.cleanarchitecture.model.login.LoginModel

interface LocalRepository {

    suspend fun addBusiness(list:List<BusinessModel>):LongArray

    suspend fun deleteBusiness(obj:BusinessModel)

    suspend fun updateBusiness(obj: BusinessModel)

    suspend fun getAllBusiness(): List<BusinessModel>

    suspend fun createUser(obj: LoginModel)

    suspend fun getUser(email:String,password: String):LoginModel?


}