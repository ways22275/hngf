package com.example.sideproject.ui.login.vo

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null,
    val errorMsg : String? = null
)
