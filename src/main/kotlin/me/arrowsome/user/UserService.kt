package me.arrowsome.user

class UserService {
    fun createUser(data: UserRegister): String =
        "fake-jwt-token"

    fun loginUser(data: UserLogin): String =
        "fake-jwt-token"
}