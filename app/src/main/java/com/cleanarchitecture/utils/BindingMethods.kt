package com.cleanarchitecture.utils

import android.widget.EditText
import androidx.databinding.BindingAdapter

object BindingMethods {
    @JvmStatic
    @BindingAdapter(value = ["error","errorVisible"], requireAll = true)
    fun emailError(view: EditText, error: String, errorVisible:Boolean) {
        if (errorVisible){
            view.error = error
        }else view.error = null
    }
}
