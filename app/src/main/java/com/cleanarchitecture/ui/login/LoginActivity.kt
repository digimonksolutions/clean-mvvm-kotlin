package com.cleanarchitecture.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.cleanarchitecture.R
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.model.login.LoginModel
import com.cleanarchitecture.model.login.LoginResponse
import com.cleanarchitecture.ui.business.BusinessActivity
import com.cleanarchitecture.ui.login.viewmodel.LoginViewModel
import com.cleanarchitecture.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity"

    private val loginViewModel:LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       /**
        * To Login From Local Database
        * */
        btnSignIn.setOnClickListener {
            if (etSignInEmail.text.isNotEmpty() && etSignInPassword.text.isNotEmpty()){
                loginViewModel.getLoginFromDB(etSignInEmail.text.toString().trim(),etSignInPassword.text.toString().trim())
            }else{
                loginViewModel.getLoginFromDB("test@gmail.com","000000")
            }
        }

        /**
         *  TO Login Using Remote Api
         * */
       /* btnSignIn.setOnClickListener {
        val keyValues = HashMap<String,String>()
            if (etSignInEmail.text.isNotEmpty() && etSignInPassword.text.isNotEmpty()){
                keyValues["email"] = etSignInEmail.text.toString().trim()
                keyValues["password"] = etSignInPassword.text.toString().trim()
                keyValues["devicetoken"] = "wq"
                keyValues["android"] = "android"
            }else{
                keyValues["email"] = "developerandroid@gmail.com"
                keyValues["password"] = "123456"
                keyValues["devicetoken"] = "wq"
                keyValues["android"] = "android"
            }
            loginViewModel.getLogin(keyValues)
        }*/

        btnSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        /**
         *  Observer Response From Remote API
        * */
        lifecycleScope.launch {
            loginViewModel.loginState.collect{
                handleLogin(it)
            }
        }

        /**
         *  Observer Response From Local Database
        * */
        lifecycleScope.launch {
            loginViewModel.loginDBState.collect{
                handleLoginFromDB(it)
            }
        }


    }

    private fun handleLoginFromDB(resource: Resource<LoginModel>) {
        when(resource.status){
            Resource.Status.SUCCESS -> {
                pbLoader.visibility = View.GONE
                val data = resource.data
                if (data != null){
                    startActivity(Intent(this,BusinessActivity::class.java))
                }else{
                    Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show()
                }
            }
            Resource.Status.ERROR -> {
                pbLoader.visibility = View.GONE
                Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.LOADING -> {
                pbLoader.visibility = View.VISIBLE
            }
            Resource.Status.EMPTY -> {
                pbLoader.visibility = View.GONE
            }
        }

    }

    fun handleLogin(state:Resource<LoginResponse>){
        when(state.status){
            Resource.Status.SUCCESS -> {
                pbLoader.visibility = View.GONE
                val data = state.data
                if (data?.status == "success"){
                    startActivity(Intent(this,BusinessActivity::class.java))
                }else{
                    Toast.makeText(this, data?.message, Toast.LENGTH_SHORT).show()
                }
            }
            Resource.Status.ERROR -> {
                pbLoader.visibility = View.GONE
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            Resource.Status.LOADING -> {
                pbLoader.visibility = View.VISIBLE
            }
            Resource.Status.EMPTY -> {
                pbLoader.visibility = View.GONE
            }
        }
    }
}