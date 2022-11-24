package com.cleanarchitecture.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.domain.interactor.GetLoginUseCase
import com.cleanarchitecture.domain.response.Response
import com.cleanarchitecture.model.login.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val getLoginUseCase : GetLoginUseCase):ViewModel() {

private val loginStateFlow = MutableStateFlow<Resource<LoginResponse>>(Resource.empty())

    val loginState :StateFlow<Resource<LoginResponse>>
            get() = loginStateFlow

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

}