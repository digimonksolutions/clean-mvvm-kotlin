package com.cleanarchitecture.ui.business.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleanarchitecture.R
import com.cleanarchitecture.model.business.BusinessModel
import kotlinx.android.synthetic.main.item_raw_layout.view.*


class BusinessAdapter(
    val context: Context,
    private var items: List<BusinessModel?>?,
    private val itemClick: (BusinessModel) -> Unit
) : RecyclerView.Adapter<BusinessAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.item_raw_layout, parent, false)
        return ItemHolder(row, itemClick)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindItem(items!![position]!!)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    inner class ItemHolder(val view: View, val itemClick: (BusinessModel) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val imageItem = view.ivBannerRaw
        private val contentTitle = view.tvTittleRaw

        @SuppressLint("RestrictedApi")
        fun bindItem(item: BusinessModel) {
            Glide.with(context).load(
                item.image_path
            ).into(imageItem)
            contentTitle.setTypeface(null, Typeface.BOLD);
            contentTitle.text = item.name
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}