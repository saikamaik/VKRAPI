package com.diploma.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import graphql.GraphQLException
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.joda.time.DateTime
import java.util.*

const val SECRET = "a%s*&##fsj@"

data class TokenKey(val email: String, val id : Int) : Principal

class JwtConfig(){

    companion object Constants {

    private const val issuer = "User"
    private const val jwtRealm = "com.diploma"

    private const val CLAIM_USEREMAIL = "userEmail"
    private const val CLAIM_USERID = "userId"

    private const val validityInMs = 36_000_00 * 24 // 1 day
    }

    private val algorithm = Algorithm.HMAC512(SECRET)

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

    fun configureKtorFeature(config: JWTAuthenticationProvider.Configuration) = with(config) {
        verifier(verifier)
        realm = jwtRealm
        validate {
            val userEmail = it.payload.getClaim(CLAIM_USEREMAIL).asInt()
            val userId = it.payload.getClaim(CLAIM_USERID).asString()

            if (userId != null && userEmail != null) {
                JwtUser(userEmail, userId)
            } else {
                null
            }
        }
    }

    //TODO DecodeJWTToken

    data class JwtUser(val userEmail: Int, val userId: String): Principal

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
