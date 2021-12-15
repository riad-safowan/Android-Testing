package com.riadsafowan.androidtest


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("", "1234", "1234")
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validateRegistrationInput("abir", "1234", "1234")
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exist and returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("riad", "1234", "1234")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("abir", "", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `password less than 2 characters returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("abir", "1", "1")
        assertThat(result).isFalse()
    }

    @Test
    fun `repeated password not same returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("abir", "1234", "4567")
        assertThat(result).isFalse()
    }

}