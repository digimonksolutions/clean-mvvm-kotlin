package com.cleanarchitecture.data.repository

import android.content.Context
import com.cleanarchitecture.data.source.sharedPref.TinyDB
import com.cleanarchitecture.domain.repository.SharedPrefRepository

class SharedPrefRepositoryImpl(private val context: Context):SharedPrefRepository {

    companion object{

        const val USER_LOGIN = "USER_LOGIN"
    }

    val tinyDB = TinyDB(context)

    override fun setLogin(LoggedIn: Boolean) {
        tinyDB.putBoolean(USER_LOGIN,LoggedIn)
    }

    override fun getLogin(): Boolean {
        return tinyDB.getBoolean(USER_LOGIN)
    }
}