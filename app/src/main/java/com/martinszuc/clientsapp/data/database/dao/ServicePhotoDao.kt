package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.ServicePhoto

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
interface ServicePhotoDao {
    @Insert
    suspend fun insertPhoto(servicePhoto: ServicePhoto)

    @Query("SELECT * FROM service_photos WHERE service_id = :serviceId")
    suspend fun getPhotosForService(serviceId: Int): List<ServicePhoto>
    @Delete
    suspend fun deletePhoto(servicePhoto: ServicePhoto)

    @Query("DELETE FROM service_photos WHERE service_id = :serviceId")
    suspend fun deletePhotosForService(serviceId: Int)
}
