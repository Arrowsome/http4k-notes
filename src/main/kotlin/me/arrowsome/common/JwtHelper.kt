package me.arrowsome.common

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import java.time.Instant
import java.time.temporal.ChronoUnit

class JwtHelper {

    fun generateToken(): String {
        try {
            return JWT
                .create()
                .withIssuer(ISSUER)
                .withSubject(SUBJECT)
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS))
                .sign(algorithm)
        } catch (e: JWTCreationException) {
            throw ApiException.ServerError("Server is malfunctioning!")
        }
    }

    fun verifyToken(token: String): Boolean {
        return try {
            verifier.verify(token)
            true
        } catch (exc: TokenExpiredException) {
            false
        } catch (exc: SignatureVerificationException) {
            false
        } catch (e: JWTVerificationException) {
            throw ApiException.ServerError("Server is malfunctioning!")
        }
    }

    private val verifier by lazy {
        val algorithm = Algorithm.HMAC256(SECRET)
        JWT
            .require(algorithm)
            .build()
    }

    private val algorithm = Algorithm.HMAC256(SECRET)

    companion object {
        private const val ISSUER = "me.arrowsome.notes"
        private const val SUBJECT = "User Authentication Token"
        private const val SECRET = "secret4jwt@notes"
    }
}