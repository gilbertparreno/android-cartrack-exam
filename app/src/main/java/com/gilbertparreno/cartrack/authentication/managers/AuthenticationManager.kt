package com.gilbertparreno.cartrack.authentication.managers

import android.util.Base64
import com.gilbertparreno.cartrack.BuildConfig
import com.gilbertparreno.cartrack.authentication.exceptions.AuthenticationException
import com.gilbertparreno.cartrack.core.security.Encryption
import com.gilbertparreno.cartrack.core.room.daos.UserDao
import com.gilbertparreno.cartrack.core.sharedPreferences.UserSharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationManager @Inject constructor(
    private val userDao: UserDao,
    private val userSharedPreferences: UserSharedPreferences
) {

    @Throws(AuthenticationException::class)
    suspend fun authenticateUser(email: String, password: String) {
        val user = userDao.findUser(email = email)
        if (
            user != null &&
            decryptPassword(user.passwordSalt, user.passwordIv, user.passwordEncrypted) == password
        ) {
            return userSharedPreferences.setUserDetails(
                userId = user.uid,
                email = user.email
            )
        }
        throw AuthenticationException("User not found.")
    }

    private fun decryptPassword(salt: String, iv: String, encrypted: String): String {
        return String(
            Encryption.decrypt(
                hashMapOf(
                    "salt" to Base64.decode(salt, Base64.NO_WRAP),
                    "iv" to Base64.decode(iv, Base64.NO_WRAP),
                    "encrypted" to Base64.decode(encrypted, Base64.NO_WRAP)
                ),
                BuildConfig.MASTER_PASSWORD.toCharArray()
            )!!,
            Charsets.UTF_8
        )
    }
}