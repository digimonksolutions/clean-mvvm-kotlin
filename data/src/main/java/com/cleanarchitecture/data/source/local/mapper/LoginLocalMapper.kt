package com.cleanarchitecture.data.source.local.mapper

import com.cleanarchitecture.data.source.local.model.LoginDBModel
import com.cleanarchitecture.model.login.LoginModel

class LoginLocalMapper {
    
    fun mapFromLocal(loginDBModel: LoginDBModel?): LoginModel? {

        return if (loginDBModel == null) null
         else LoginModel(
            loginDBModel.admin, loginDBModel.created_at, loginDBModel.devicetoken,
            loginDBModel.devicetype, loginDBModel.email, loginDBModel.password,loginDBModel.firstname,
            loginDBModel.id, loginDBModel.lastname,loginDBModel.updated_at,loginDBModel.user_type,loginDBModel.zip_code
        )
    }

    fun mapToLocal(loginModel: LoginModel): LoginDBModel {
        return LoginDBModel(
            loginModel.admin,
            loginModel.created_at,
            loginModel.devicetoken.toString(),
            loginModel.devicetype.toString(),
            loginModel.email,
            loginModel.password,
            loginModel.firstname,
            loginModel.id,
            loginModel.lastname,
            loginModel.updated_at,
            loginModel.user_type,
            loginModel.zip_code
        )
    }
}