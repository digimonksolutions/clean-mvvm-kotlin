package com.cleanarchitecture.domain.repository

interface SharedPrefRepository {

    fun setLogin(LoggedIn:Boolean)
    fun getLogin():Boolean
}