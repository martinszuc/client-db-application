package com.martinszuc.clientsapp.di

import android.content.Context
import com.martinszuc.clientsapp.data.database.dao.ClientDao
import com.martinszuc.clientsapp.data.database.dao.ServiceCategoryDao
import com.martinszuc.clientsapp.data.database.dao.ServiceDao
import com.martinszuc.clientsapp.data.database.dao.ServicePhotoDao
import com.martinszuc.clientsapp.data.database.dao.ServiceTypeDao
import com.martinszuc.clientsapp.data.local.LocalStorageRepository
import com.martinszuc.clientsapp.data.repository.ClientRepository
import com.martinszuc.clientsapp.data.repository.ServiceCategoryRepository
import com.martinszuc.clientsapp.data.repository.ServiceRepository
import com.martinszuc.clientsapp.data.repository.ServiceTypeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    @Provides
    @Singleton
    fun provideServiceCategoryRepository(
        dao: ServiceCategoryDao
    ): ServiceCategoryRepository {
        return ServiceCategoryRepository(dao)
    }

    @Provides
    @Singleton
    fun provideServiceTypeRepository(
        dao: ServiceTypeDao
    ): ServiceTypeRepository {
        return ServiceTypeRepository(dao)
    }
    // Provide LocalStorageRepository
    @Provides
    @Singleton
    fun provideLocalStorageRepository(@ApplicationContext context: Context): LocalStorageRepository {
        return LocalStorageRepository(context)
    }
}