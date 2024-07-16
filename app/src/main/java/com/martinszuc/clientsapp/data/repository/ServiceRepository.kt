package com.martinszuc.clientsapp.data.repository

import com.martinszuc.clientsapp.data.database.dao.ServiceDao
import com.martinszuc.clientsapp.data.entity.Service
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(
    private val serviceDao: ServiceDao
) {
    suspend fun insertService(service: Service) = serviceDao.insertService(service)
    suspend fun getServicesForClient(clientId: Int) = serviceDao.getServicesForClient(clientId)
    suspend fun getServices() = serviceDao.getServices()
    suspend fun searchServices(query: String) = serviceDao.searchServices(query)
}