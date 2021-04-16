package com.gilbertparreno.cartrack.core.room.di

import android.content.Context
import androidx.room.Room
import com.gilbertparreno.cartrack.core.room.daos.UserDao
import com.gilbertparreno.cartrack.core.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideRoomDatabase(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "cartrack-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}