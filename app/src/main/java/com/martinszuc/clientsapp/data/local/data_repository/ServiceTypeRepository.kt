package com.martinszuc.clientsapp.data.local.data_repository

import com.martinszuc.clientsapp.data.database.dao.ServiceTypeDao
import com.martinszuc.clientsapp.data.entity.ServiceType
import javax.inject.Inject

class ServiceTypeRepository @Inject constructor(
    private val dao: ServiceTypeDao
) {
    suspend fun getAllServiceTypes(): List<ServiceType> = dao.getAllServiceTypes()
    suspend fun insertServiceType(type: ServiceType) = dao.insertServiceType(type)
}