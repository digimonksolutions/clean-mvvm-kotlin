package com.cleanarchitecture.utils

import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide

object BindingMethods {
    @JvmStatic
    @BindingAdapter(value = ["error","errorVisible"], requireAll = true)
    fun emailError(view: EditText, error: String, errorVisible:Boolean) {
        if (errorVisible){
            view.error = error
        }else view.error = null
    }

    @JvmStatic
    @BindingAdapter("setImageByGlide")
    fun setImageUsingGlide(view:ImageView,setImageByGlide:String){
        Glide.with(view.context).load(setImageByGlide).into(view)
    }
}
