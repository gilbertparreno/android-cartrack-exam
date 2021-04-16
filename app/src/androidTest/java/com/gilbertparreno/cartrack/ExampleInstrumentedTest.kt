package com.gilbertparreno.cartrack

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gilbertparreno.cartrack.core.security.Encryption
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun encryption() {
        val masterPassword = "masterPassword".toCharArray()
        val map = Encryption.encrypt("admin1234".toByteArray(Charsets.UTF_8), masterPassword)
        val decryptedByteArray = Encryption.decrypt(map, masterPassword)
        val decryptedPassword = String(decryptedByteArray!!, Charsets.UTF_8)
        assertEquals("admin1234", decryptedPassword)
    }
}