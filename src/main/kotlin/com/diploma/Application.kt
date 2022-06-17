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
import com.diploma.utils.TokenKey
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

val jwtConfig = JwtConfig

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.hikari()

    install(GraphQL) {
        playground = true
        schema { schemaValue() }
    }

    install(CORS) {
        anyHost()
        method(HttpMethod.Options)
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

    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            validate {
                TokenKey(it.payload.getClaim("email").asString(), it.payload.getClaim("id").asInt())
            }
        }
    }

    routing {

//        route("/") {
//            post("/graphql") {
//                graphQLRequest = call.receive<GraphQLRequest>()
//                if (("authUser" in graphQLRequest!!.query) or ("userRegistration" in graphQLRequest!!.query)) {
//                    KGraphQL.schema { schemaValue() }.execute(graphQLRequest!!.query)
//                } else {
//                    call.respond(HttpStatusCode(401, "not authorized(("))
//                }
//            }
//            authenticate {
            header("Content-type", "application/json; charset=utf-8") {
                post("/graphql") {
                    val graphqlRequest = call.receive<GraphQLRequest>()
                    KGraphQL.schema { schemaValue() }.execute(graphqlRequest.query)
                }
            }
//            }

            get("/user") {
                val users = transaction {
                    User.selectAll().map { User.toShowMap(it) }
                }
                call.respond(users)
            }
        }
    }
//}

