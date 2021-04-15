package com.gilbertparreno.cartrack.main.factories

import com.gilbertparreno.cartrack.core.networking.entities.UserApi
import com.gilbertparreno.cartrack.main.entities.User

object UserFactory {
    fun createUserFromUserApiList(usersApi: List<UserApi>): List<User> {
        return usersApi.map {
            val address = listOf(
                it.address.street,
                it.address.city,
                it.address.zipCode
            ).joinToString(", ")
            User(
                id = it.id,
                name = it.name,
                username = it.username,
                email = it.email,
                address = address,
                company = it.company.name,
                website = it.website,
                phone = it.phone,
                latLng = it.address.latLng
            )
        }
    }
}