package com.gilbertparreno.cartrack

import com.gilbertparreno.cartrack.core.utils.security.Crypto
import org.junit.Assert.assertEquals
import org.junit.Test

class SecurityUnitTest {
    @Test
    fun crypto_Success() {
        val rawPassword = "admin1234"
        val map = Crypto.encrypt(rawPassword)
        val decryptedPassword = Crypto.decrypt(map)
        assertEquals(rawPassword, decryptedPassword)
    }
}