package me.arrowsome.user

sealed class UserException : RuntimeException() {
    class UserAlreadyExists: UserException()
    class UserWeakCredentials: UserException()
}