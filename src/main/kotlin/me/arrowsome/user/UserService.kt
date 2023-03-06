package me.arrowsome.user

import me.arrowsome.common.ApiException
import me.arrowsome.common.JwtHelper

class UserService(
    private val jwtHelper: JwtHelper,
    private val userDao: UserDao,
) {
    fun registerUser(data: UserRegister): String {
        if (!data.email.matches(".+@.+\\..+".toRegex()))
            throw ApiException.Invalid("${data.email} is not a valid email address!")

        if (!data.password.matches("\\S{8,}".toRegex()))
            throw ApiException.Invalid("${data.password} is not a strong password!")

        if (userDao.anyUser(data.email))
            throw ApiException.Conflict("Existing user!")

        userDao.insertUser(data.toUserEntity())

        return jwtHelper.generateToken()
    }

    fun loginUser(data: UserLogin): String {
        val credentialExists = userDao.anyUserWithCredentials(
            data.email,
            data.password,
        )

        if (!credentialExists)
            throw ApiException.NotFound("no user was found with provided credentials!")

        return jwtHelper.generateToken()
    }
}