package io.bastillion.manage.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BCryptEncoderTest() {

    @Test
    fun testPasswordMatchAndMismatch() {
        val tool: BCryptEncoder = BCryptEncoder()
        val encodePassword = tool
                .hashAndSalt("helloBCrypt")
        System.out.println("I HashedAndSalted a password")
        assertFalse(tool.matches("hElloBcrypt", encodePassword))
        assertFalse(tool.matches("helloBcryp", encodePassword))
        assertFalse(tool.matches("helloBcrypt", encodePassword))
        assertTrue(tool.matches("helloBCrypt", encodePassword))
    }

    @Test
    fun testPasswordsAreUniquelySalted() {
        val tool: BCryptEncoder = BCryptEncoder()
        val pw = "helloBCrypt"
        val encodePassword1 = tool
                .hashAndSalt(pw)
        val encodePassword2 = tool.hashAndSalt(pw)
        assertNotEquals(encodePassword1, encodePassword2)
    }
}