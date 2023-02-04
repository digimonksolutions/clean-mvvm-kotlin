package com.cleanarchitecture.ui.fragment.login.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.domain.interactor.CreateUserDbUseCase
import com.cleanarchitecture.domain.interactor.GetLoginUseCase
import com.cleanarchitecture.domain.interactor.GetUserDBUseCase
import com.cleanarchitecture.domain.response.Response
import com.cleanarchitecture.domain.model.login.LoginModel
import com.cleanarchitecture.domain.model.login.LoginResponse
import com.digi.base_module.base.BaseViewModel
import com.cleanarchitecture.ui.fragment.login.LoginFragmentDirections
import com.cleanarchitecture.utils.AppConstants
import com.digi.base_module.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val getLoginUseCase: GetLoginUseCase,
    private val getUserDBUseCase: GetUserDBUseCase,
    private val createUserDbUseCase: CreateUserDbUseCase
) : BaseViewModel() {

    init {
        createDummyUser()
    }

     val loginState = MutableLiveData<Resource<LoginResponse>>(Resource.loading())
     val loginDBState = MutableLiveData<Event<Resource<LoginModel>>>()
    var email:String = ""
    var password:String = ""
    val loader = ObservableBoolean()



    /**
     * Get Login response From Remote API
     * */
    fun getLogin(hashMap: HashMap<String, String>) {
        loginState.value = Resource.loading()
        loader.set(true)
        viewModelScope.launch {
            val response = getLoginUseCase.execute(LoginResponse::class.java, AppConstants.getLogin, hashMap)
            withContext(Dispatchers.Main) {
                when (response.status) {
                    Response.Status.SUCCESS -> {
                        if(response.data?.status == "success") {
                            loader.set(false)
                            loginState.value = Resource.success(response.data)
                            goToDetailsPage()
                        }else{
                            loader.set(false)
                            loginState.value = Resource.empty(response.data?.message)
                        }
                    }
                    Response.Status.ERROR -> {
                        loader.set(false)
                        loginState.value = Resource.error(response.message.toString())
                    }
                }
            }
        }
    }

    /**
     *  Get Login Data From Local DataBase
     * */
    fun getLoginFromDB(email: String, password: String) {
        loginDBState.value = Event(Resource.loading())
        loader.set(true)
        viewModelScope.launch {
            val response = getUserDBUseCase.execute(email, password)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    loader.set(false)
                    loginDBState.value = Event(Resource.success(response))
                    goToDetailsPage()
                }
                else {
                    loader.set(false)
                    loginDBState.value = Event(Resource.empty())
                }
            }
        }
    }

    fun createDummyUser() {
        viewModelScope.launch {
            createUserDbUseCase.execute(
                LoginModel(
                    admin = 0,
                    created_at = "",
                    devicetoken = "",
                    devicetype = "",
                    email = "test@gmail.com",
                    password = "000000",
                    firstname = "test",
                    id = 0,
                    lastname = "test",
                    updated_at = "",
                    user_type = 0,
                    zip_code = "000000"
                )
            )
        }
    }

    /**
     *  Handle Sign In Click Using Data Binding
     * */
    fun onSignInClick(){
        if (email.isNotEmpty() && password.isNotEmpty()) {
            getLoginFromDB(
                email.trim(),
                password.trim()
            )
        } else {
            getLoginFromDB("test@gmail.com", "000000")
        }
    }

    /**
     *  Handle Sign In Click Using Data Binding ( For API )
     * */
    fun onSignInClickAPICall(){
        val keyValues = HashMap<String,String>()
        if (email.isNotEmpty() && password.isNotEmpty()){
            keyValues["email"] = email.trim()
            keyValues["password"] = password.trim()
            keyValues["devicetoken"] = "wq"
            keyValues["android"] = "android"
        }else{
            keyValues["email"] = "developerandroid@gmail.com"
            keyValues["password"] = "123456"
            keyValues["devicetoken"] = "wq"
            keyValues["android"] = "android"
        }
        getLogin(keyValues)
    }

    fun goToDetailsPage(){
        navigate(LoginFragmentDirections.loginToBusinessList())
    }

    fun goToSignupPage(){
        navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
    }

}