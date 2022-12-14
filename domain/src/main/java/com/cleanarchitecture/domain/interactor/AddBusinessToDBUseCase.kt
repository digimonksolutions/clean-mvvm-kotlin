package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.repository.LocalRepository
import com.cleanarchitecture.domain.model.business.BusinessModel

class AddBusinessToDBUseCase(private val localRepository: LocalRepository) {
    suspend fun execute(list: List<BusinessModel>):LongArray{
         return localRepository.addBusiness(list)
    }
}