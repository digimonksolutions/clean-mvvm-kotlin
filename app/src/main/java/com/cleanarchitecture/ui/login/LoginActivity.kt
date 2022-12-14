package com.cleanarchitecture.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.databinding.ActivityLoginBinding
import com.cleanarchitecture.domain.model.login.LoginModel
import com.cleanarchitecture.domain.model.login.LoginResponse
import com.cleanarchitecture.ui.business.BusinessActivity
import com.cleanarchitecture.ui.login.viewmodel.LoginViewModel
import com.cleanarchitecture.ui.signup.SignUpActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity"

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginViewModel = loginViewModel

        /**
         *  Handle Response From Remote API
         * */
        lifecycleScope.launch {
            loginViewModel.loginState.collect {
                handleLogin(it)
            }
        }

        /**
         *  Handle Response From Local Database
         * */
        lifecycleScope.launch {
            loginViewModel.loginDBState.collect {
                handleLoginFromDB(it)
            }
        }


    }

    fun navigateToSignUp(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }

    private fun handleLoginFromDB(resource: Resource<LoginModel>) {
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                startActivity(Intent(this, BusinessActivity::class.java))
            }
            Resource.Status.ERROR -> {
                Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.EMPTY -> {
                Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show()
            }
            Resource.Status.LOADING -> {}
        }

    }

    private fun handleLogin(state: Resource<LoginResponse>) {
        when (state.status) {
            Resource.Status.SUCCESS -> {
                startActivity(Intent(this, BusinessActivity::class.java))
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