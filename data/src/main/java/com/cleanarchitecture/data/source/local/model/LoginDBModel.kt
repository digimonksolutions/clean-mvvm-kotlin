package com.cleanarchitecture.data.source.local.model

import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class LoginDBModel(
    @ColumnInfo(name = "admin") val admin: Int,
    @ColumnInfo(name = "created_at") val created_at: String,
    @ColumnInfo(name = "devicetoken") val devicetoken: String,
    @ColumnInfo(name = "devicetype") val devicetype: String,
    @PrimaryKey @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "updated_at") val updated_at: String,
    @ColumnInfo(name = "user_type") val user_type: Int,
    @ColumnInfo(name = "zip_code") val zip_code: String
)