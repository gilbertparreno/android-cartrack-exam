package com.gilbertparreno.cartrack.core.utils.security

import okhttp3.internal.and
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object Crypto {

    private const val password: String = "p@\$\$w0rd"
    private val random = SecureRandom()
    private val salt = ByteArray(256)

    init {
        random.nextBytes(salt)
    }

    fun encrypt(dataToEncrypt: String): HashMap<String, String> {
        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, 1324, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val ivRandom = SecureRandom()
        val iv = ByteArray(16)
        ivRandom.nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(dataToEncrypt.toByteArray())

        return hashMapOf(
            "iv" to byteArrayToHexString(iv),
            "salt" to byteArrayToHexString(salt),
            "encrypted" to byteArrayToHexString(encrypted)
        )
    }

    fun decrypt(map: HashMap<String, String>): String? {
        val salt = map["salt"] ?: return null
        val iv = map["iv"] ?: return null
        val encrypted = map["encrypted"] ?: return null
        val pbKeySpec = PBEKeySpec(password.toCharArray(), hexStringToByteArray(salt), 1324, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(hexStringToByteArray(iv))
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)

        cipher.doFinal(hexStringToByteArray(encrypted)).let {
            return String(it, Charsets.UTF_8)
        }
    }

    private fun byteArrayToHexString(b: ByteArray): String {
        val sb = StringBuffer(b.size * 2)
        for (i in b.indices) {
            val v: Int = b[i] and 0xff
            if (v < 16) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(v))
        }
        return sb.toString().toUpperCase()
    }

    private fun hexStringToByteArray(s: String): ByteArray? {
        val b = ByteArray(s.length / 2)
        for (i in b.indices) {
            val index = i * 2
            val v = s.substring(index, index + 2).toInt(16)
            b[i] = v.toByte()
        }
        return b
    }
}
