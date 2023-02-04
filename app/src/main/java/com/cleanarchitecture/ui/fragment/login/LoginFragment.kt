package com.cleanarchitecture.ui.fragment.login

import android.os.Bundle
import android.widget.Toast
import com.cleanarchitecture.R
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.databinding.FragmentLoginBinding
import com.digi.base_module.base.BaseFragment
import com.cleanarchitecture.ui.fragment.login.viewmodel.LoginViewModel
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
        handleLoginFromDB()

    }

    private fun handleLogin() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state.status) {
                Resource.Status.SUCCESS -> {}
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

    private fun handleLoginFromDB() {
        viewModel.loginDBState.observe(viewLifecycleOwner){ state ->
            when (state.getContentIfNotHandled()?.status) {
                Resource.Status.SUCCESS -> {}
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), state.peekContent().message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.EMPTY -> {
                    Toast.makeText(requireContext(), "User Not Found", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {}
                else -> {}
            }
        }
    }

}