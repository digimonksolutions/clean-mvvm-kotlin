package com.cleanarchitecture.ui.signup.viewmodel

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.domain.interactor.CreateUserDbUseCase
import com.cleanarchitecture.domain.model.login.LoginModel
import com.cleanarchitecture.utils.AppConstants
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Observable

class SignUpViewModel(private val createUserDbUseCase: CreateUserDbUseCase):ViewModel() {

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

}