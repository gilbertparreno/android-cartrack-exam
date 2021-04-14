package com.gilbertparreno.cartrack.main.entities

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: String,
    val company: String,
    val website: String,
    val phone: String,
    val latLng: LatLng
) : Serializable