package com.martinszuc.clientsapp.di

import com.martinszuc.clientsapp.data.database.dao.ClientDao
import com.martinszuc.clientsapp.data.database.dao.ServiceDao
import com.martinszuc.clientsapp.data.repository.ClientRepository
import com.martinszuc.clientsapp.data.repository.ServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideServiceRepository(serviceDao: ServiceDao): ServiceRepository {
        return ServiceRepository(serviceDao)
    }
}