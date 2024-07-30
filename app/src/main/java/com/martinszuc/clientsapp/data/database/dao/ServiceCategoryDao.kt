package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.ServiceCategory

@Dao
interface ServiceCategoryDao {
    @Query("SELECT * FROM service_categories")
    suspend fun getAllCategories(): List<ServiceCategory>

    @Insert
    suspend fun insertCategory(category: ServiceCategory)
}