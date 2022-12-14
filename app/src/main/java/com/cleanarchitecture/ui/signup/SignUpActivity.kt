package com.cleanarchitecture.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cleanarchitecture.R
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.databinding.ActivitySignUpBinding
import com.cleanarchitecture.domain.model.login.LoginModel
import com.cleanarchitecture.ui.business.BusinessActivity
import com.cleanarchitecture.ui.signup.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {
    private val signUpViewModel: SignUpViewModel by viewModel()
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signUpViewModel = signUpViewModel

        lifecycleScope.launch {
            signUpViewModel.signUpState.collect{
                handleSignUp(it)
            }
        }
    }

    private fun handleSignUp(resource: Resource<LoginModel>) {
        when(resource.status){
            Resource.Status.SUCCESS ->{
                startActivity(Intent(this,BusinessActivity::class.java))
            }
            else -> {}
        }

    }
}