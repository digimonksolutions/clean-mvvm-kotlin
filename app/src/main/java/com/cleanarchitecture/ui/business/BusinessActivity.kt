package com.cleanarchitecture.ui.business

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.databinding.ActivityBusinessBinding
import com.cleanarchitecture.domain.model.business.BusinessModel
import com.cleanarchitecture.domain.model.business.BusinessResponse
import com.cleanarchitecture.ui.business.adapter.BusinessAdapter
import com.cleanarchitecture.ui.business.viewmodel.BusinessViewModel
import kotlinx.android.synthetic.main.activity_business.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusinessActivity : AppCompatActivity() {
    private val businessViewModel: BusinessViewModel by viewModel()
    private lateinit var binding: ActivityBusinessBinding
    val TAG = "BusinessActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.businessViewModel = businessViewModel

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
        lifecycleScope.launch {
            businessViewModel.businessDBState.collect {
                handelBusinessFromDB(it)
            }
        }

    }

    private fun handelBusinessFromDB(resource: Resource<List<BusinessModel>>) {
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                binding.rvBusiness.adapter = BusinessAdapter(this, resource.data) {
                    Toast.makeText(this, "clicked ${it.name}", Toast.LENGTH_SHORT).show()
                }
            }
            Resource.Status.EMPTY -> {
                Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.ERROR -> {
                Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.LOADING -> {}
        }

    }

    fun handelBusiness(state: Resource<BusinessResponse>) {
        when (state.status) {
            Resource.Status.SUCCESS -> {
                val data = state.data
                rvBusiness.adapter = BusinessAdapter(this, data?.data) {
                    Toast.makeText(this, "Clicked ${it.name}", Toast.LENGTH_SHORT).show()
                }
            }
            Resource.Status.ERROR -> {
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.EMPTY -> {
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.LOADING -> {}
        }


    }
}