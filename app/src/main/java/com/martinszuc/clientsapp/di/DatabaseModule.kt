package com.martinszuc.clientsapp.di

import android.content.Context
import androidx.room.Room
import com.martinszuc.clientsapp.data.database.AppDatabase
import com.martinszuc.clientsapp.data.database.dao.ClientDao
import com.martinszuc.clientsapp.data.database.dao.ServiceCategoryDao
import com.martinszuc.clientsapp.data.database.dao.ServiceDao
import com.martinszuc.clientsapp.data.database.dao.ServicePhotoDao
import com.martinszuc.clientsapp.data.database.dao.ServiceTypeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "kozmetika_database"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .addMigrations(AppDatabase.MIGRATION_2_3)
            .build()
    }

    @Provides
    fun provideClientDao(db: AppDatabase): ClientDao {
        return db.clientDao()
    }

    @Provides
    fun provideServiceDao(db: AppDatabase): ServiceDao {
        return db.serviceDao()
    }
    @Provides
    fun provideServiceCategoryDao(db: AppDatabase): ServiceCategoryDao {
        return db.serviceCategoryDao()
    }
    @Provides
    fun provideServiceTypeDao(db: AppDatabase): ServiceTypeDao {
        return db.serviceTypeDao()
    }    @Provides
    fun provideServicePhotoDao(db: AppDatabase): ServicePhotoDao {
        return db.servicePhotoDao()
    }
}