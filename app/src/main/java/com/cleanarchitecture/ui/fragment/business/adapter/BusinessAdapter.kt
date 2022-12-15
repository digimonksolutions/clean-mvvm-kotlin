package com.cleanarchitecture.ui.fragment.business.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.cleanarchitecture.R
import com.cleanarchitecture.databinding.ItemRawLayoutBinding
import com.cleanarchitecture.domain.model.business.BusinessModel
import com.cleanarchitecture.ui.base.BaseAdapters
import com.cleanarchitecture.ui.base.BaseViewHolder
import com.cleanarchitecture.ui.fragment.business.viewmodel.BusinessViewModel

class BusinessAdapter(
    val context: Context,
    items: List<BusinessModel>?,
    viewModel: BusinessViewModel
): BaseAdapters<ItemRawLayoutBinding,BusinessViewModel, BusinessModel>(items,viewModel){

    override val layoutId: Int
        get() = R.layout.item_raw_layout

    override fun bind(
        binding: ItemRawLayoutBinding,
        item: BusinessModel,
        position: Int,
        holder: BaseViewHolder<ItemRawLayoutBinding>,
        viewModel: BusinessViewModel
    ) {
        Glide.with(context).load(item.image_path).into(binding.ivBannerRaw)
        binding.tvTittleRaw.text = item.name
        binding.businessModel = item
    }

}