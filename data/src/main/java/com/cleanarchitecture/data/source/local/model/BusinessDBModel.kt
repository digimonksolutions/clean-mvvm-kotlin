package com.cleanarchitecture.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Business")
data class BusinessDBModel(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category_status") val category_status: String?,
    @ColumnInfo(name = "created_at") val created_at: String?,
    @ColumnInfo(name = "deleted_at") val deleted_at: String?,
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "image_path") val image_path: String?,
    @ColumnInfo(name = "order_num") val order_num: Int?,
    @ColumnInfo(name = "updated_at") val updated_at: String?
)