package com.cleanarchitecture.data.repository

import android.content.Context
import com.cleanarchitecture.data.source.local.db.BusinessDatabase
import com.cleanarchitecture.data.source.local.mapper.BusinessLocalMapper
import com.cleanarchitecture.data.source.local.mapper.LoginLocalMapper
import com.cleanarchitecture.domain.repository.LocalRepository
import com.cleanarchitecture.model.business.BusinessModel
import com.cleanarchitecture.model.login.LoginModel

class LocalRepositoryImpl(private val context: Context,private val businessLocalMapper: BusinessLocalMapper, private val loginLocalMapper: LoginLocalMapper):LocalRepository {
    override suspend fun addBusiness(list: List<BusinessModel>): LongArray {
        return BusinessDatabase.getDatabase(context).businessDao().addBusiness(list.map { businessLocalMapper.mapToLocal(it) })
    }

    override suspend fun deleteBusiness(obj: BusinessModel) {
        return BusinessDatabase.getDatabase(context).businessDao().deleteBusiness(businessLocalMapper.mapToLocal(obj))
    }

    override suspend fun updateBusiness(obj: BusinessModel) {
        return BusinessDatabase.getDatabase(context).businessDao().updateBusiness(businessLocalMapper.mapToLocal(obj))
    }

    override suspend fun getAllBusiness(): List<BusinessModel>{
        return BusinessDatabase.getDatabase(context).businessDao()
            .getBusiness().map {
             businessDBModel ->
                businessLocalMapper.mapFromLocal(businessDBModel)
            }
        }

    override suspend fun createUser(obj: LoginModel) {
        BusinessDatabase.getDatabase(context).businessDao().createUser(loginLocalMapper.mapToLocal(obj))
    }

    override suspend fun getUser(email: String, password: String): LoginModel?{
        return  loginLocalMapper.mapFromLocal(BusinessDatabase.getDatabase(context).businessDao().getUser(email,password))
    }

}