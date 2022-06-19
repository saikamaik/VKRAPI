package com.diploma

import com.apurebase.kgraphql.GraphQL
import com.apurebase.kgraphql.KGraphQL
import com.diploma.dataBase.DatabaseFactory
import com.diploma.graphQLShema.schemaValue
import com.diploma.model.GraphQLRequest
import com.diploma.model.User
import com.diploma.utils.JwtConfig
import com.diploma.utils.TokenKey
import graphql.VisibleForTesting
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

val jwtConfig = JwtConfig

@Suppress("unused") // Referenced in application.conf
fun Application.module() {

    DatabaseFactory.hikari()

    install(GraphQL) {
        playground = true
        schema { schemaValue() }
    }

    install(Authentication) {
        jwt("auth-jwt") {
            jwtConfig.configureKtorFeature(this)
        }
    }

    install(CORS) {
        anyHost()
        method(HttpMethod.Options)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.AccessControlAllowOrigin)
        allowSameOrigin = true
        allowCredentials = true
        allowNonSimpleContentTypes = true
    }
    install(CallLogging)
    install(ContentNegotiation) {
        gson()
    }
    install(DoubleReceive)

    routing {

//        header("Authorization", "Bearer") {
//            post("/graphql") {
//                val graphQLRequest = call.receive<GraphQLRequest>()
//                if (("auth" in graphQLRequest.query) or ("reg" in graphQLRequest.query)) {
//                    KGraphQL.schema { schemaValue() }.execute(graphQLRequest.query)
//                } else {
//                    call.respond(HttpStatusCode(401, "not authorized"))
//                }
//            }
//        }
//        authenticate("auth-jwt") {
            route("/graphql") {
                post() {
                    val graphqlRequest = call.receive<GraphQLRequest>()
                    KGraphQL.schema { schemaValue() }.execute(graphqlRequest.query)
                }
            }
//        }
    }
}

