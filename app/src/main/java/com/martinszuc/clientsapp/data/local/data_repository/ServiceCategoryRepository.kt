package com.martinszuc.clientsapp.data.local.data_repository

import com.martinszuc.clientsapp.data.database.dao.ServiceCategoryDao
import com.martinszuc.clientsapp.data.entity.ServiceCategory
import javax.inject.Inject

class ServiceCategoryRepository @Inject constructor(
    private val dao: ServiceCategoryDao
) {
    suspend fun getAllCategories(): List<ServiceCategory> = dao.getAllCategories()
    suspend fun insertCategory(category: ServiceCategory) = dao.insertCategory(category)
}