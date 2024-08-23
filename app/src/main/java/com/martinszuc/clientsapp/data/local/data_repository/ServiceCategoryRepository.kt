package com.martinszuc.clientsapp.data.local.data_repository

import com.martinszuc.clientsapp.data.database.dao.ServiceCategoryDao
import com.martinszuc.clientsapp.data.entity.ServiceCategory
import javax.inject.Inject

/**
 * Project: database application
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

class ServiceCategoryRepository @Inject constructor(
    private val dao: ServiceCategoryDao
) {
    suspend fun getAllCategories(): List<ServiceCategory> = dao.getAllCategories()
    suspend fun insertCategory(category: ServiceCategory) = dao.insertCategory(category)
}