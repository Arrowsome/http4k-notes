package me.arrowsome.user

data class UserRegister(
    val email: String,
    val password: String,
)

data class Token(
    val token: String,
)

data class UserLogin(
    val email: String,
    val password: String,
)