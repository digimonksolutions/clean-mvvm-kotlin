package com.cleanarchitecture.model.login

data class LoginModel(
    val admin: Int,
    val created_at: String,
    val devicetoken: Any,
    val devicetype: Any,
    val email: String,
    val password: String,
    val firstname: String,
    val id: Int,
    val lastname: String,
    val updated_at: String,
    val user_type: Int,
    val zip_code: String
)