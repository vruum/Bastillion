package io.bastillion.manage.services

import org.bouncycastle.crypto.generators.BCrypt
import com.google.common.io.BaseEncoding
import java.security.SecureRandom

class BCryptEncoder(private val defaultCost: Int = 8) {

    val codec: BaseEncoding = BaseEncoding.base64()
    val secureRandom: SecureRandom= SecureRandom()


    fun hashAndSalt(password: String): String {
        val salt = ByteArray(16)
        secureRandom.nextBytes(salt)
        return hashAndEncode(password, salt)
    }

    private fun hashAndEncode(password: String, salt: ByteArray): String {
        val pwBytes = BCrypt.passwordToByteArray(password.toCharArray())
        val encodedHash = codec.encode(encode(pwBytes, salt, defaultCost))
        val encodedSalt = codec.encode(salt)
        return "bcrypt:${defaultCost}:${encodedSalt}:${encodedHash}"
    }


    fun matches(passwordInput: String, hashedSaltedAndEncoded: String): Boolean {
        val split = hashedSaltedAndEncoded.split(":")
        if (split.size != 4) {
            throw RuntimeException("invalidly encoded password")
        }
        val encodedSalt = split[2].replace(":", "")
        val cost = Integer.parseInt(split[1].replace(":", ""))
        val salt = codec.decode(encodedSalt)
        val inputHashedSaltedAndEncoded = hashAndEncode(passwordInput, salt)
        return inputHashedSaltedAndEncoded == hashedSaltedAndEncoded
    }

    private fun encode(password: ByteArray, salt: ByteArray, cost: Int): ByteArray {
        return BCrypt.generate(password, salt, cost)
    }
//
//    fun matches(rawPasswordInput: ByteArray, HashedAndSalted: ByteArray): Boolean {
//        BCrypt.generate()
//    }
}