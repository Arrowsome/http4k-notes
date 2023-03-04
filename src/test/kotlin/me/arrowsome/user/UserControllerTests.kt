package me.arrowsome.user

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.mockk.every
import io.mockk.mockk
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserControllerTests {
    private lateinit var userController: UserController
    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        userService = mockk()
        userController = UserController(userService)
    }

    @Test
    fun `register a new user on the server`() {
        val request = Request(POST, "/api/users").with(userController.registerLens of USER_REGISTER)
        every { userService.createUser(userController.registerLens.extract(request)) } returns SAMPLE_JWT_TOKEN
        val response = userController.registerUser(request)

        assertThat(response, hasStatus(CREATED) and hasBody(userController.tokenLens, equalTo(TOKEN)))
    }

    @Test
    fun `login a user on the server`() {
        val request = Request(POST, "/api/users/login").with(userController.loginLens of USER_LOGIN)
        every { userService.loginUser(userController.loginLens.extract(request)) } returns SAMPLE_JWT_TOKEN
        val response = userController.loginUser(request)

        assertThat(response, hasStatus(OK) and hasBody(userController.tokenLens, equalTo(TOKEN)))
    }

    companion object {
        private const val SAMPLE_JWT_TOKEN = "jwt_generated_token"

        private val USER_REGISTER = UserRegister(
            email = "john.doe@example.com",
            password = "John@123!",
        )

        private val TOKEN = Token(
            token = SAMPLE_JWT_TOKEN,
        )

        private val USER_LOGIN = UserLogin(
            email = "john.doe@example.com",
            password = "John@123!",
        )
    }

}





