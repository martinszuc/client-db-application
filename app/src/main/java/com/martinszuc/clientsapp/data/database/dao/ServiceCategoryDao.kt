package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.ServiceCategory

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

@Dao
interface ServiceCategoryDao {
    @Query("SELECT * FROM service_categories")
    suspend fun getAllCategories(): List<ServiceCategory>

    @Insert
    suspend fun insertCategory(category: ServiceCategory)
}