package com.gilbertparreno.cartrack.core.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gilbertparreno.cartrack.core.room.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM users WHERE email == :email")
    suspend fun findUser(email: String): User?

    @Insert
    suspend fun insertUsers(vararg user: User)
}