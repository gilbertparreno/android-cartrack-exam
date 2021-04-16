package com.gilbertparreno.cartrack.core.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo val email: String,
    @ColumnInfo val passwordSalt: String,
    @ColumnInfo val passwordIv: String,
    @ColumnInfo val passwordEncrypted: String
)