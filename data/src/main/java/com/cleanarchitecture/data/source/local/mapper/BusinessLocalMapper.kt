package com.cleanarchitecture.data.source.local.mapper

import com.cleanarchitecture.data.source.local.model.BusinessDBModel
import com.cleanarchitecture.domain.model.business.BusinessModel

class BusinessLocalMapper {

    fun mapFromLocal(businessDBModel: BusinessDBModel): BusinessModel {
        return BusinessModel(
            businessDBModel.category_status, businessDBModel.created_at, businessDBModel.deleted_at,
            businessDBModel.id, businessDBModel.image_path, businessDBModel.name,
            businessDBModel.order_num, businessDBModel.updated_at
        )
    }

    fun mapToLocal(businessModel: BusinessModel): BusinessDBModel {
        return BusinessDBModel(
            businessModel.name,
            businessModel.category_status,
            businessModel.created_at,
            businessModel.deleted_at,
            businessModel.id,
            businessModel.image_path,
            businessModel.order_num,
            businessModel.updated_at
        )
    }
}