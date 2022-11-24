package com.cleanarchitecture.ui.business.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.domain.interactor.GetBusinessUseCase
import com.cleanarchitecture.domain.response.Response
import com.cleanarchitecture.model.business.BusinessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusinessViewModel(private val getBusinessUseCase: GetBusinessUseCase):ViewModel() {

    private val businessStateFlow = MutableStateFlow<Resource<BusinessResponse>>(Resource.empty())

    val businessState: StateFlow<Resource<BusinessResponse>>
        get() = businessStateFlow

    fun getBusiness(){
        businessStateFlow.value = Resource.loading()
        viewModelScope.launch {
            val response = getBusinessUseCase.execute(BusinessResponse::class.java,"category")
            withContext(Dispatchers.Main){
                when(response.status){
                    Response.Status.SUCCESS -> {
                        businessStateFlow.value = Resource.success(response.data)
                    }
                    Response.Status.ERROR -> {
                        businessStateFlow.value = Resource.error(response.message.toString())
                    }
                }
            }
        }
    }

}