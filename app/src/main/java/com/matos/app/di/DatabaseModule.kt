package com.matos.app.di

import android.content.Context
import androidx.room.Room
import com.matos.app.data.database.AppDatabase
import com.matos.app.data.database.dao.ClientDao
import com.matos.app.data.database.dao.ServiceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideClientDao(db: AppDatabase): ClientDao {
        return db.clientDao()
    }

    @Provides
    fun provideServiceDao(db: AppDatabase): ServiceDao {
        return db.serviceDao()
    }
}