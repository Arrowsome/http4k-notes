package me.arrowsome.user

class UserRepository {
    fun anyUser(email: String): Boolean {
        return false
    }

    fun anyUserWithCredentials(email: String, password: String): Boolean {
        return false
    }
}