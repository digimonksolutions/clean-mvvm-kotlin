package com.cleanarchitecture.model.business

data class BusinessResponse(
    val `data`: List<BusinessModel?>?,
    val message: String?,
    val status: String?
)