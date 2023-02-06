package com.cleanarchitecture.ui.fragment.login

import android.os.Bundle
import android.widget.Toast
import com.cleanarchitecture.R
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.databinding.FragmentLoginBinding
import com.digi.base_module.base.BaseFragment
import com.cleanarchitecture.ui.fragment.login.viewmodel.LoginViewModel
import com.digi.base_module.extensions.observe
import com.digi.base_module.extensions.toastMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val layoutId: Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()

    override fun onReady(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        /**
         *  Handle Response From Remote API
         * */
        handleLogin()


        /**
         *  Handle Response From Local Database
         * */
        if (viewModel.getUserLogin()){
            viewModel.goToDetailsPage()
        }else{
            handleLoginFromDB()
        }

    }

    private fun handleLogin() {
        observe(viewModel.loginState){ state ->
            when (state.status) {
                Resource.Status.SUCCESS -> {}
                Resource.Status.ERROR -> {
                    requireContext().toastMessage(state.message)
                }
                Resource.Status.EMPTY -> {
                    requireContext().toastMessage(state.message)
                }
                Resource.Status.LOADING -> {}
            }
        }
    }

    private fun handleLoginFromDB() {

        observe(viewModel.loginDBState){ state ->
            when (state.getContentIfNotHandled()?.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.saveUserLogin(true)
                }
                Resource.Status.ERROR -> {
                    requireContext().toastMessage(state.peekContent().message)
                }
                Resource.Status.EMPTY -> {
                    requireContext().toastMessage("User Not Found")
                }
                Resource.Status.LOADING -> {}
                else -> {}
            }
        }
    }

    override fun onNetworkAvailable() {

    }

    override fun onNetworkLost() {

    }

}