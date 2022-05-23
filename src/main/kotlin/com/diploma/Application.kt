package com.diploma

import com.apurebase.kgraphql.GraphQL
import com.apurebase.kgraphql.KGraphQL
import com.diploma.dataBase.DatabaseFactory
import com.diploma.databaseMutationController.UserMutation
import com.diploma.graphQLShema.schemaValue
import com.diploma.model.GraphQLRequest
import com.diploma.model.LoginBody
import com.diploma.model.User
import com.diploma.utils.JwtConfig
import graphql.ExecutionInput
import io.ktor.server.netty.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


fun main(args: Array<String>): Unit = EngineMain.main(args)

val jwtConfig = JwtConfig()

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.hikari()

    install(CORS) {
        anyHost()
    }
    install(CallLogging)
    install(ContentNegotiation) {
        gson()
    }

    install(Authentication) {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }

    routing {

        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        post("/login") {
            val loginBody = call.receive<LoginBody>()

            val user = UserMutation().authUser(loginBody.email, loginBody.password)

            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials!")
                return@post
            }
            call.respond(user)
        }

        authenticate  {
            post("/graphql") {
                val graphqlRequest = call.receive<GraphQLRequest>()
                KGraphQL.schema{schemaValue()}.execute(graphqlRequest.query)
            }
        }

        get("/user") {
            val users = transaction {
                User.selectAll().map { User.toShowMap(it) }
            }
            call.respond(users)
        }
    }
}
