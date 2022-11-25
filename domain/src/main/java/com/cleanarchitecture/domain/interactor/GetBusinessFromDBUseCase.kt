package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.repository.LocalRepository
import com.cleanarchitecture.model.business.BusinessModel
import kotlinx.coroutines.flow.Flow

class GetBusinessFromDBUseCase(private val localRepository: LocalRepository) {

    suspend fun execute():List<BusinessModel>{
        return localRepository.getAllBusiness()
    }

}