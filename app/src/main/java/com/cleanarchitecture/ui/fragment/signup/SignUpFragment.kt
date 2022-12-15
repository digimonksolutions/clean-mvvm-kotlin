package com.cleanarchitecture.ui.fragment.signup

import android.os.Bundle
import com.cleanarchitecture.R
import com.cleanarchitecture.databinding.FragmentSignUpBinding
import com.cleanarchitecture.ui.base.BaseFragment
import com.cleanarchitecture.ui.fragment.signup.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding,SignUpViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_sign_up
    override val viewModel: SignUpViewModel by viewModel()

    override fun onReady(savedInstanceState: Bundle?) {

    }

}