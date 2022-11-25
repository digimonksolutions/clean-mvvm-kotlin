package com.cleanarchitecture.ui.business

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cleanarchitecture.R
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.model.business.BusinessModel
import com.cleanarchitecture.model.business.BusinessResponse
import com.cleanarchitecture.ui.business.adapter.BusinessAdapter
import com.cleanarchitecture.ui.business.viewmodel.BusinessViewModel
import kotlinx.android.synthetic.main.activity_business.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusinessActivity : AppCompatActivity() {
    private val businessViewModel:BusinessViewModel by viewModel()
    val TAG = "BusinessActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)

        /**
        *  Get Business From Remote API
        * */
       /*
        businessViewModel.getBusiness()
        lifecycleScope.launch {
            businessViewModel.businessState.collect{
                        handelBusiness(it)
            }
        }
        */

        /**
         *  Get Business From Local DataBase
        * */
        businessViewModel.getBusinessFromDB()
        lifecycleScope.launch{
            businessViewModel.businessDBState.collect{
                handelBusinessFromDB(it)
            }
        }

    }

    private fun handelBusinessFromDB(resource: Resource<List<BusinessModel>>) {
        when(resource.status){
            Resource.Status.SUCCESS ->{
                pbBusiness.visibility = View.GONE
                val data = resource.data
                if (data!!.isNotEmpty()){
                    rvBusiness.visibility = View.VISIBLE
                    rvBusiness.layoutManager = LinearLayoutManager(this)
                    rvBusiness.adapter = BusinessAdapter(this,data){
                    }
                }else{
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    rvBusiness.visibility = View.GONE
                }
                Log.e(TAG,"SUCCESS => ${resource.data.toString()} )")
            }
            Resource.Status.EMPTY -> {
                pbBusiness.visibility = View.GONE
                rvBusiness.visibility = View.GONE

            }
            Resource.Status.LOADING -> {
                pbBusiness.visibility = View.VISIBLE
                rvBusiness.visibility = View.GONE
                Log.e(TAG, "LOADING")
            }
            else -> {
                pbBusiness.visibility = View.GONE
                rvBusiness.visibility = View.GONE
                Log.e("DBDATA", "ELSE" + resource.data.toString())

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