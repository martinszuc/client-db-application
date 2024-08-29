package com.martinszuc.clientsapp.di

import android.content.Context
import com.martinszuc.clientsapp.data.database.dao.ClientDao
import com.martinszuc.clientsapp.data.database.dao.ServiceDao
import com.martinszuc.clientsapp.data.database.dao.ServicePhotoDao
import com.martinszuc.clientsapp.data.local.LocalStorageRepository
import com.martinszuc.clientsapp.data.local.data_repository.ClientRepository
import com.martinszuc.clientsapp.data.local.data_repository.ServiceRepository
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
object AppModule {
    @Provides
    @Singleton
    fun provideClientRepository(clientDao: ClientDao): ClientRepository {
        return ClientRepository(clientDao)
    }

    @Provides
    @Singleton
    fun provideServiceRepository(serviceDao: ServiceDao,servicePhotoDao: ServicePhotoDao): ServiceRepository {
        return ServiceRepository(serviceDao, servicePhotoDao)
    }

    // Provide LocalStorageRepository
    @Provides
    @Singleton
    fun provideLocalStorageRepository(@ApplicationContext context: Context): LocalStorageRepository {
        return LocalStorageRepository(context)
    }
}