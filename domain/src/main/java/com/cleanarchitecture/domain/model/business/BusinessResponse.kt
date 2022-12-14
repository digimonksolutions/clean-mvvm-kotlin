package com.cleanarchitecture.domain.model.business

data class BusinessResponse(
    val `data`: List<BusinessModel?>?,
    val message: String?,
    val status: String?
)