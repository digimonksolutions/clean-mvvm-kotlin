package com.cleanarchitecture.ui.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.domain.interactor.CreateUserDbUseCase
import com.cleanarchitecture.model.login.LoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(private val createUserDbUseCase: CreateUserDbUseCase):ViewModel() {

    private val signUpStateFlow = MutableStateFlow<Resource<LoginModel>>(Resource.empty())

    val signUpState: StateFlow<Resource<LoginModel>>
        get() = signUpStateFlow


    fun createUser(obj:LoginModel){
        signUpStateFlow.value = Resource.loading()
        viewModelScope.launch {
            createUserDbUseCase.execute(obj)
            withContext(Dispatchers.Main){
                signUpStateFlow.value = Resource.success(null)
            }
        }
    }


}