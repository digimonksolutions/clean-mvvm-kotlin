package com.cleanarchitecture.ui.login.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.data.source.local.model.LoginDBModel
import com.cleanarchitecture.domain.interactor.CreateUserDbUseCase
import com.cleanarchitecture.domain.interactor.GetLoginUseCase
import com.cleanarchitecture.domain.interactor.GetUserDBUseCase
import com.cleanarchitecture.domain.response.Response
import com.cleanarchitecture.model.login.LoginModel
import com.cleanarchitecture.model.login.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.PasswordAuthentication

class LoginViewModel(private val getLoginUseCase : GetLoginUseCase,private val getUserDBUseCase: GetUserDBUseCase,private val createUserDbUseCase: CreateUserDbUseCase):ViewModel() {

    init {
        createDummyUser()
    }

private val loginStateFlow = MutableStateFlow<Resource<LoginResponse>>(Resource.empty())
private val loginDBStateFlow = MutableStateFlow<Resource<LoginModel>>(Resource.empty())

    val loginState :StateFlow<Resource<LoginResponse>>
            get() = loginStateFlow
    val loginDBState :StateFlow<Resource<LoginModel>>
        get() = loginDBStateFlow

        /**
         * Get Login response From Remote API
        * */
        fun getLogin(hashMap: HashMap<String,String>){
            loginStateFlow.value = Resource.loading()
            viewModelScope.launch {
                val response = getLoginUseCase.execute(LoginResponse::class.java,"login",hashMap)
                withContext(Dispatchers.Main){
                    when(response.status){
                        Response.Status.SUCCESS ->{
                            loginStateFlow.value = Resource.success(response.data)
                        }
                        Response.Status.ERROR -> {
                            loginStateFlow.value = Resource.error(response.message.toString())
                        }
                    }
                }
            }
        }

    /**
     *  Get Login Data From Local DataBase
     * */
    fun getLoginFromDB(email: String,password:String){
        loginDBStateFlow.value = Resource.loading()
        viewModelScope.launch {
            val response = getUserDBUseCase.execute(email,password)
            withContext(Dispatchers.Main){
                loginDBStateFlow.value = Resource.success(response)
            }
        }
    }

    fun createDummyUser(){
        viewModelScope.launch {
            createUserDbUseCase.execute(LoginModel(
                0,
                "",
                "",
                "",
                "test@gmail.com",
                "000000",
                "test",
                0,
                "test",
                "",
                0,
                "000000"
            ))
        }


    }

}