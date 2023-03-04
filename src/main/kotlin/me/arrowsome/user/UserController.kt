package me.arrowsome.user

import org.http4k.core.*
import org.http4k.format.Moshi.auto

class UserController(private val userService: UserService) {

    fun registerUser(request: Request): Response {
        val registerData = registerLens.extract(request)

        val token = userService.registerUser(registerData)

        return Response(Status.CREATED)
            .with(tokenLens of Token(token))
    }

    fun loginUser(request: Request): Response {
        val loginData = loginLens.extract(request)

        val token = userService.loginUser(loginData)

        return Response(Status.OK)
            .with(tokenLens of Token(token))
    }

    val registerLens = Body.auto<UserRegister>().toLens()
    val tokenLens = Body.auto<Token>().toLens()
    val loginLens = Body.auto<UserLogin>().toLens()
}