package com.matos.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matos.app.data.database.dao.ClientDao
import com.matos.app.data.database.dao.ServiceDao
import com.matos.app.data.entity.Client
import com.matos.app.data.entity.Service

@Database(entities = [Client::class, Service::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun serviceDao(): ServiceDao
}