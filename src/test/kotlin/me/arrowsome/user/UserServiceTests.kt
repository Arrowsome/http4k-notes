package me.arrowsome.user

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.equalToIgnoringCase
import io.mockk.every
import io.mockk.mockk
import me.arrowsome.common.ApiException
import me.arrowsome.common.JwtHelper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserServiceTests {
    private lateinit var userService: UserService
    private lateinit var jwtHelper: JwtHelper
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        jwtHelper = mockk()
        userRepository = mockk()
        userService = UserService(jwtHelper, userRepository)
    }

    @Test
    fun `user creation returns a jwt`() {
        every { jwtHelper.generateToken() } returns SAMPLE_JWT_TOKEN
        every { userRepository.anyUser(USER_REGISTER.email) } returns false
        val token = userService.registerUser(USER_REGISTER)
        assertThat(token, equalTo(SAMPLE_JWT_TOKEN))
    }

    @Test
    fun `user creation with invalid email throws invalid exception`() {
        val userWithInvalidEmail = USER_REGISTER.copy(email = "john.doe@.com")
        val exc = assertThrows<ApiException.Invalid> {
            every { jwtHelper.generateToken() } returns SAMPLE_JWT_TOKEN
            userService.registerUser(userWithInvalidEmail)
        }
        assertNotNull(exc)
        assertThat(exc.msg, equalToIgnoringCase("${userWithInvalidEmail.email} is not a valid email address!"))
    }

    @Test
    fun `user creation with invalid password throws invalid exception`() {
        val userWithInvalidPassword = USER_REGISTER.copy(password = "John@1!")
        val exc = assertThrows<ApiException.Invalid> {
            every { jwtHelper.generateToken() } returns SAMPLE_JWT_TOKEN
            userService.registerUser(userWithInvalidPassword)
        }
        assertNotNull(exc)
        assertThat(exc.msg, equalToIgnoringCase("${userWithInvalidPassword.password} is not a strong password!"))
    }

    @Test
    fun `user login returns jwt`() {
        every { jwtHelper.generateToken() } returns SAMPLE_JWT_TOKEN
        every { userRepository.anyUserWithCredentials(USER_LOGIN.email, USER_LOGIN.password) } returns true
        val token = userService.loginUser(USER_LOGIN)
        assertThat(token, equalTo(SAMPLE_JWT_TOKEN))
    }

    @Test
    fun `user login throws not found on no user found`() {
        val exc = assertThrows<ApiException.NotFound> {
            every { jwtHelper.generateToken() } returns SAMPLE_JWT_TOKEN
            every { userRepository.anyUserWithCredentials(USER_LOGIN.email, USER_LOGIN.password) } returns false
            userService.loginUser(USER_LOGIN)
        }
        assertNotNull(exc)
        assertThat(exc.msg, equalToIgnoringCase("no user was found with provided credentials!"))
    }

    companion object {
        private val USER_REGISTER = UserRegister(
            email = "john.doe@gmail.com",
            password = "John@123!"
        )

        private const val SAMPLE_JWT_TOKEN = "jwt_generated_token"

        private val TOKEN = Token(
            token = SAMPLE_JWT_TOKEN,
        )

        private val USER_LOGIN = UserLogin(
            email = "john.doe@gmail.com",
            password = "John@123!"
        )
    }
}