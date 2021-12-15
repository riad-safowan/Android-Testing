package com.riadsafowan.androidtest

object RegistrationUtil {

    private val users = listOf("riad", "safowan", "redwan")

    /**
     * the input is not valid if...
     * ...the username/password is empty
     * ...the username is already taken
     * ...confirm password is not same as real password
     * ...password contains less than 2 digits
     */
    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (userName.isNotEmpty() &&
            userName !in users &&
            password.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            password.length >= 2 &&
            password == confirmPassword
        ) return true
        return false
    }
}