package com.cleanarchitecture.data.source.local.dao

import androidx.room.*
import com.cleanarchitecture.data.source.local.model.BusinessDBModel
import com.cleanarchitecture.data.source.local.model.LoginDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {

    @Query("SELECT * FROM Business")
    suspend fun getBusiness(): List<BusinessDBModel>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBusiness(obj:List<BusinessDBModel>): LongArray

    @Delete
    suspend fun deleteBusiness(businessDBModel: BusinessDBModel)

    @Update
    suspend fun updateBusiness(businessDBModel: BusinessDBModel)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(loginDBModel: LoginDBModel)

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    suspend fun getUser(email:String , password:String) : LoginDBModel

}