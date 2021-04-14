package com.gilbertparreno.cartrack.core.networking.services

import com.gilbertparreno.cartrack.core.networking.entities.UserApi
import retrofit2.http.GET

interface UserService {

    @GET("users")
    suspend fun getUsers(): List<UserApi>
}