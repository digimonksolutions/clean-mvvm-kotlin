package com.cleanarchitecture.ui.fragment.business.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.domain.interactor.AddBusinessToDBUseCase
import com.cleanarchitecture.domain.interactor.GetBusinessFromDBUseCase
import com.cleanarchitecture.domain.interactor.GetBusinessUseCase
import com.cleanarchitecture.domain.response.Response
import com.cleanarchitecture.domain.model.business.BusinessModel
import com.cleanarchitecture.domain.model.business.BusinessResponse
import com.digi.base_module.base.BaseViewModel
import com.cleanarchitecture.ui.fragment.business.BusinessListFragmentDirections
import com.cleanarchitecture.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusinessViewModel(
    private val getBusinessUseCase: GetBusinessUseCase,
    private val getBusinessFromDBUseCase: GetBusinessFromDBUseCase,
    private val addBusinessToDBUseCase: AddBusinessToDBUseCase
) : BaseViewModel() {

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

    val recyclerViewVisibility = ObservableBoolean()
    val loaderVisibility = ObservableBoolean()


    /**
     * Get Business Data From Remote API
     * */
    fun getBusiness() {
        businessStateFlow.value = Resource.loading()
        loaderVisibility.set(true)
        recyclerViewVisibility.set(false)
        viewModelScope.launch {
            val response = getBusinessUseCase.execute(BusinessResponse::class.java, AppConstants.getCategory)
            withContext(Dispatchers.Main) {
                when (response.status) {
                    Response.Status.SUCCESS -> {
                        if (response.data?.status == "success") {
                            recyclerViewVisibility.set(true)
                            loaderVisibility.set(false)
                            businessStateFlow.value = Resource.success(response.data)
                        }else{
                            recyclerViewVisibility.set(false)
                            loaderVisibility.set(false)
                            businessDbStateFlow.value = Resource.empty(response.message)
                        }
                    }
                    Response.Status.ERROR -> {
                        recyclerViewVisibility.set(false)
                        loaderVisibility.set(false)
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
        recyclerViewVisibility.set(false)
        loaderVisibility.set(true)
        viewModelScope.launch {
            val response = getBusinessFromDBUseCase.execute()
            withContext(Dispatchers.Main) {
                if (response.isNotEmpty()) {
                    loaderVisibility.set(false)
                    recyclerViewVisibility.set(true)
                    businessDbStateFlow.value = Resource.success(response)
                } else {
                    loaderVisibility.set(false)
                    recyclerViewVisibility.set(false)
                    businessDbStateFlow.value = Resource.empty("No Data Found")
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
                    category_status = "",
                    created_at = "",
                    deleted_at = "",
                    id = i,
                    image_path = AppConstants.dummyImgUrl,
                    name = "Dummy $i",
                    order_num = i,
                    updated_at = ""
                )
            )
        }
        return arrayList
    }

    fun navigateToDetailsPage(businessModel: BusinessModel){
        navigate(BusinessListFragmentDirections.actionBusinessListFragmentToBusinessDetailsFragment(businessModel))
    }
}