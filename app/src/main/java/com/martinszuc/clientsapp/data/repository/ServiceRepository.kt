package com.martinszuc.clientsapp.data.repository

import com.martinszuc.clientsapp.data.database.dao.ServiceDao
import com.martinszuc.clientsapp.data.database.dao.ServicePhotoDao
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.data.entity.ServicePhoto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(
    private val serviceDao: ServiceDao,
    private val servicePhotoDao: ServicePhotoDao
) {
    suspend fun insertService(service: Service) = serviceDao.insertService(service)
    suspend fun getServicesForClient(clientId: Int) = serviceDao.getServicesForClient(clientId)
    suspend fun getServices() = serviceDao.getServices()
    suspend fun searchServices(query: String) = serviceDao.searchServices(query)
    suspend fun insertPhoto(servicePhoto: ServicePhoto) = servicePhotoDao.insertPhoto(servicePhoto)
    suspend fun getPhotosForService(serviceId: Int) = servicePhotoDao.getPhotosForService(serviceId)
    suspend fun deletePhoto(servicePhoto: ServicePhoto) = servicePhotoDao.deletePhoto(servicePhoto)
    suspend fun deletePhotosForService(serviceId: Int) = servicePhotoDao.deletePhotosForService(serviceId)
    suspend fun deleteService(service: Service) = serviceDao.deleteService(service)
}