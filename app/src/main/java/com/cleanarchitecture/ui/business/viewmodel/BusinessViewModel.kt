package com.cleanarchitecture.ui.business.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.domain.interactor.AddBusinessToDBUseCase
import com.cleanarchitecture.domain.interactor.GetBusinessFromDBUseCase
import com.cleanarchitecture.domain.interactor.GetBusinessUseCase
import com.cleanarchitecture.domain.response.Response
import com.cleanarchitecture.model.business.BusinessModel
import com.cleanarchitecture.model.business.BusinessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusinessViewModel(
    private val getBusinessUseCase: GetBusinessUseCase,
    private val getBusinessFromDBUseCase: GetBusinessFromDBUseCase,
    private val addBusinessToDBUseCase: AddBusinessToDBUseCase
) : ViewModel() {

    init {
        addBusinessToDB(createDummyBusiness())
    }

    private val businessStateFlow = MutableStateFlow<Resource<BusinessResponse>>(Resource.empty())

    val businessState: StateFlow<Resource<BusinessResponse>>
        get() = businessStateFlow

    val businessDBState: StateFlow<Resource<List<BusinessModel>>>
        get() = businessDbStateFlow

    private val businessDbStateFlow =
        MutableStateFlow<Resource<List<BusinessModel>>>(Resource.empty())


    /**
     * Get Business Data From Remote API
     * */
    fun getBusiness() {
        businessStateFlow.value = Resource.loading()
        viewModelScope.launch {
            val response = getBusinessUseCase.execute(BusinessResponse::class.java, "category")
            withContext(Dispatchers.Main) {
                when (response.status) {
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

    /**
     *  Get Business Data From Local Database
     * */
    fun getBusinessFromDB() {
        businessDbStateFlow.value = Resource.loading()
        viewModelScope.launch {
            val response = getBusinessFromDBUseCase.execute()
            withContext(Dispatchers.Main) {
                if (response.isNotEmpty()) {
                    businessDbStateFlow.value = Resource.success(response)
                } else {
                    businessDbStateFlow.value = Resource.empty()
                }
            }
        }
    }

    fun addBusinessToDB(list: List<BusinessModel>) {
        viewModelScope.launch {
            val response = addBusinessToDBUseCase.execute(list)
            withContext(Dispatchers.Main) {
                Log.e("RESPONSE", response.size.toString())
            }

        }
    }

    fun createDummyBusiness(): List<BusinessModel> {
        val arrayList: ArrayList<BusinessModel> = arrayListOf()
        for (i in 0..15) {
            arrayList.add(
                BusinessModel(
                    "",
                    "",
                    "",
                    i,
                    "https://images.unsplash.com/1/type-away-numero-dos.jpg?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80",
                    "Dummy $i",
                    i,
                    ""
                )
            )
        }
        return arrayList
    }
}