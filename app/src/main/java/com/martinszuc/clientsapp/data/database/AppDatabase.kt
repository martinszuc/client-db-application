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
    version = 3
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun serviceDao(): ServiceDao
    abstract fun serviceCategoryDao(): ServiceCategoryDao
    abstract fun serviceTypeDao(): ServiceTypeDao
    abstract fun servicePhotoDao(): ServicePhotoDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create a new temporary table without category_id and type_id columns
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `services_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `client_id` INTEGER NOT NULL,
                        `description` TEXT NOT NULL,
                        `date` INTEGER NOT NULL,
                        `price` REAL NOT NULL,
                        FOREIGN KEY(`client_id`) REFERENCES `clients`(`id`) ON DELETE CASCADE
                    )
                    """.trimIndent()
                )

                // Copy data from the old `services` table to the new table
                database.execSQL(
                    """
                    INSERT INTO `services_new` (`id`, `client_id`, `description`, `date`, `price`)
                    SELECT `id`, `client_id`, `description`, `date`, `price` FROM `services`
                    """.trimIndent()
                )

                // Drop the old table
                database.execSQL("DROP TABLE `services`")

                // Rename the new table to the original table name
                database.execSQL("ALTER TABLE `services_new` RENAME TO `services`")
            }
        }

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