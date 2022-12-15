package com.cleanarchitecture.ui.fragment.business

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.cleanarchitecture.R
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.databinding.FragmentBusinessListBinding
import com.cleanarchitecture.domain.model.business.BusinessModel
import com.cleanarchitecture.domain.model.business.BusinessResponse
import com.cleanarchitecture.ui.base.BaseFragment
import com.cleanarchitecture.ui.fragment.business.adapter.BusinessAdapter
import com.cleanarchitecture.ui.fragment.business.viewmodel.BusinessViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusinessListFragment : BaseFragment<FragmentBusinessListBinding,BusinessViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_business_list
    override val viewModel: BusinessViewModel by viewModel()

    override fun onReady(savedInstanceState: Bundle?) {

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
        viewModel.getBusinessFromDB()
        lifecycleScope.launch {
            viewModel.businessDBState.collect {
                handelBusinessFromDB(it)
            }
        }

    }
    private fun handelBusinessFromDB(resource: Resource<List<BusinessModel>>) {
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                binding.adapter = BusinessAdapter(requireContext(), resource.data,viewModel)
            }
            Resource.Status.EMPTY -> {
                Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.ERROR -> {
                Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.LOADING -> {}
        }

    }

    fun handelBusiness(state: Resource<BusinessResponse>) {
        when (state.status) {
            Resource.Status.SUCCESS -> {
                val data = state.data
                binding.adapter = BusinessAdapter(requireContext(), data?.data,viewModel)
            }
            Resource.Status.ERROR -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.EMPTY -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.LOADING -> {}
        }


    }


}