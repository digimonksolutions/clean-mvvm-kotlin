package com.cleanarchitecture.domain.model.business

data class BusinessModel(
    val category_status: String?,
    val created_at: String?,
    val deleted_at: String?,
    val id: Int?,
    val image_path: String?,
    val name: String,
    val order_num: Int?,
    val updated_at: String?
)