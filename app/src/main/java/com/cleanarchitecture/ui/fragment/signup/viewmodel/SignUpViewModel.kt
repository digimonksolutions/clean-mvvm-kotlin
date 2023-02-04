package com.cleanarchitecture.ui.fragment.signup.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.domain.interactor.CreateUserDbUseCase
import com.cleanarchitecture.domain.model.login.LoginModel
import com.digi.base_module.base.BaseViewModel
import com.cleanarchitecture.ui.fragment.signup.SignUpFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(private val createUserDbUseCase: CreateUserDbUseCase): BaseViewModel() {

    private val signUpStateFlow = MutableStateFlow<Resource<LoginModel>>(Resource.loading())

    val signUpState: StateFlow<Resource<LoginModel>>
        get() = signUpStateFlow
    var email:String = ""
    var password :String = ""
    var lastName:String = ""
    var firstName:String = ""
    var zipCode:String = ""
    var emailError= ObservableBoolean(false)
    var passwordError= ObservableBoolean(false)


    fun createUser(obj: LoginModel){
        signUpStateFlow.value = Resource.loading()
        viewModelScope.launch {
            createUserDbUseCase.execute(obj)
            withContext(Dispatchers.Main){
                signUpStateFlow.value = Resource.success(null)
                goToBusinessListFragment()
            }
        }
    }

    fun onClickOfSignUp(){
        emailError.set(false)
        passwordError.set(false)
            if (email.trim().isEmpty()){
                emailError.set(true)
            }
            if (password.isEmpty()){
                passwordError.set(true)
            }
            if (email.isNotEmpty() && password.isNotEmpty()){
                emailError.set(false)
                passwordError.set(false)
                createUser(
                    LoginModel(
                        admin = 0,
                        created_at = "",
                        devicetoken = "wp",
                         devicetype = "android",
                        email = email.trim(),
                        password = password.trim(),
                        firstname = firstName,
                        id = 0,
                        lastname = lastName,
                        updated_at = "",
                        user_type = 0,
                        zip_code = zipCode
                    )
                )
            }
    }

    fun goToBusinessListFragment(){
        navigate(SignUpFragmentDirections.actionSignupFragmentToBusinessListFragment())
    }

}