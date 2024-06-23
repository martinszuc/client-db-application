package com.matos.app.di

import com.matos.app.data.database.dao.ClientDao
import com.matos.app.data.database.dao.ServiceDao
import com.matos.app.data.repository.ClientRepository
import com.matos.app.data.repository.ServiceRepository
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