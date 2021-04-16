package com.gilbertparreno.cartrack.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gilbertparreno.cartrack.core.room.daos.UserDao
import com.gilbertparreno.cartrack.core.room.entities.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}