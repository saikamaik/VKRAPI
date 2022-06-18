package com.diploma.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import graphql.GraphQLException
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import org.joda.time.DateTime
import java.util.*

data class TokenKey(val email: String, val id : Int) : Principal
data class JwtUser(val userEmail: Int, val userId: String): Principal

object JwtConfig{

    private const val issuer = "http://127.0.0.1:4000/"
    private const val validityInMs = 36_000_00 * 24 // 1 day

    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC256(jwtSecret)
    private const val jwtRealm = "com.diploma"

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateAccessToken(email: String , id: Int): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("email", email)
        .withClaim("id", id)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    fun generateRefreshToken(email: String,  id: Int): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("email", email)
        .withClaim("id", id)
        .withClaim("date", DateTime.now().toString())
        .sign(algorithm)

    fun tokenDecode(token: String) : MutableList<String> {
        try {
            val chunks: Array<String> = token.split(".").toTypedArray()
            val decoder = Base64.getDecoder()
            val claimsValues = mutableListOf<String>()
            claimsValues.add(String(decoder.decode(chunks[1])).substringAfterLast("id\":").substringBefore(","))
            claimsValues.add(String(decoder.decode(chunks[1])).substringAfterLast("email\":\"").substringBefore("\""))
            return claimsValues
        } catch (e: IllegalArgumentException) {
            throw GraphQLException("invalid token given")
        }
    }

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

    fun configureKtorFeature(config: JWTAuthenticationProvider.Configuration) = with(config) {
        verifier(verifier)
        realm = jwtRealm
        validate {
            val userEmail = it.payload.getClaim("email").asString()
            val userId = it.payload.getClaim("id").asInt()

            if (userId != null && userEmail != null) {
                TokenKey(userEmail, userId)
            } else {
                null
            }
        }
    }

}


