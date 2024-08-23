package com.martinszuc.clientsapp.data.local.data_repository

import com.martinszuc.clientsapp.data.database.dao.ServiceTypeDao
import com.martinszuc.clientsapp.data.entity.ServiceType
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

class ServiceTypeRepository @Inject constructor(
    private val dao: ServiceTypeDao
) {
    suspend fun getAllServiceTypes(): List<ServiceType> = dao.getAllServiceTypes()
    suspend fun insertServiceType(type: ServiceType) = dao.insertServiceType(type)
}