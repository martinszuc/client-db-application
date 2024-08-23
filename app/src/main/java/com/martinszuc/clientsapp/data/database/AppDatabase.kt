package com.martinszuc.clientsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import com.martinszuc.clientsapp.data.database.dao.ClientDao
import com.martinszuc.clientsapp.data.database.dao.ServiceCategoryDao
import com.martinszuc.clientsapp.data.database.dao.ServiceDao
import com.martinszuc.clientsapp.data.database.dao.ServicePhotoDao
import com.martinszuc.clientsapp.data.database.dao.ServiceTypeDao
import com.martinszuc.clientsapp.data.entity.Client
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.data.entity.ServiceCategory
import com.martinszuc.clientsapp.data.entity.ServicePhoto
import com.martinszuc.clientsapp.data.entity.ServiceType

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

@Database(
    entities = [Client::class, Service::class, ServiceCategory::class, ServiceType::class, ServicePhoto::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun serviceDao(): ServiceDao
    abstract fun serviceCategoryDao(): ServiceCategoryDao
    abstract fun serviceTypeDao(): ServiceTypeDao
    abstract fun servicePhotoDao(): ServicePhotoDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table for ServicePhoto
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `service_photos` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `service_id` INTEGER NOT NULL,
                        `photoUri` TEXT NOT NULL,
                        FOREIGN KEY(`service_id`) REFERENCES `services`(`id`) ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
            }
        }
    }
}