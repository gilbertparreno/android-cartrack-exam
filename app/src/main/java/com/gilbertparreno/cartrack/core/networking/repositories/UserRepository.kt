package com.gilbertparreno.cartrack.core.networking.repositories

import com.gilbertparreno.cartrack.core.networking.services.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUsers() = userService.getUsers()
}