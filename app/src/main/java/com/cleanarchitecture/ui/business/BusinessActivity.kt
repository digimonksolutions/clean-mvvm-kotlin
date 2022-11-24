package com.cleanarchitecture.ui.business

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cleanarchitecture.R
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.model.business.BusinessResponse
import com.cleanarchitecture.model.login.LoginModel
import com.cleanarchitecture.ui.business.adapter.BusinessAdapter
import com.cleanarchitecture.ui.business.viewmodel.BusinessViewModel
import kotlinx.android.synthetic.main.activity_business.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusinessActivity : AppCompatActivity() {
    private val businessViewModel:BusinessViewModel by viewModel()
    val TAG = "BusinessActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)
        businessViewModel.getBusiness()
        lifecycleScope.launch {
            businessViewModel.businessState.collect{
                        handelBusiness(it)
            }
        }

    }

    fun handelBusiness(state:Resource<BusinessResponse>){
        when(state.status){
            Resource.Status.SUCCESS -> {
                pbBusiness.visibility = View.GONE
                val data = state.data
                if (data?.status == "success"){
                 rvBusiness.visibility = View.VISIBLE
                 rvBusiness.layoutManager = LinearLayoutManager(this)
                    rvBusiness.adapter = BusinessAdapter(this,data.data){
                    }
                }else{
                    Toast.makeText(this, data?.message, Toast.LENGTH_SHORT).show()
                    rvBusiness.visibility = View.GONE
                }
                Log.e(TAG,"SUCCESS => ${state.data.toString()} )")

            }
            Resource.Status.ERROR -> {
                pbBusiness.visibility = View.GONE
                rvBusiness.visibility = View.GONE
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "ERROR => ${state.message}")
            }
            Resource.Status.LOADING -> {
                pbBusiness.visibility = View.VISIBLE
                rvBusiness.visibility = View.GONE
                Log.e(TAG, "LOADING")

            }
            Resource.Status.EMPTY -> {
                pbBusiness.visibility = View.GONE
                rvBusiness.visibility = View.GONE
            }
        }


    }
}