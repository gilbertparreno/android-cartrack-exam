package com.gilbertparreno.cartrack.core.networking.entities

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class UserApi(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) {
    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        @SerializedName("geo") val latLng: LatLng,
        @SerializedName("zipcode") val zipCode: String
    )

    data class Company(
        val name: String,
        val catchPhrase: String,
        val bs: String
    )
}