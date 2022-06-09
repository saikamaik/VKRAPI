package com.diploma

import com.diploma.model.GraphQLRequest
import com.diploma.model.User.id
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.assertEquals
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun t01UserUpdateTest() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/") {
                addHeader("Content-Type", "application/json")
                val graphQLRequest = GraphQLRequest(
                    """
                    {query: 'mutation {
                      updateUser(id: 9, name: "update") {
                        id
                        name
                      }
                    }'}
                """.trimIndent()
                )
                setBody(graphQLRequest.query)
            }.apply {
                assertEquals(
                    """
                    {
                          "id": 9,
                          "name": "update"
                      }
                """.trimIndent(), response.content
                )
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
    @Test
    fun t03UserCreateTest() {
    withTestApplication({ module(testing = true)}) {
        handleRequest(HttpMethod.Post, "/") {
            addHeader("Content-Type", "application/json")
            val graphQLRequest = GraphQLRequest(
                """
                    {query: 'mutation {
                      createUser(id: $id, name: "update") {
                        id
                        name
                      }
                    }'}
                """.trimIndent()
            )
            setBody(graphQLRequest.query)
        }.apply {
            assertEquals(
                """
                    {
                          "id": 9,
                          "name": "update"
                      }
                """.trimIndent(), response.content
            )
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
}
    @Test
    fun t02UserDelTest() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/") {
                addHeader("Content-Type", "application/json")
                val graphQLRequest = GraphQLRequest(
                    """
                    {query: "mutation {
                      deleteUser(id: $id)
                    }"}
                """.trimIndent()
                )
                setBody(graphQLRequest.query)
            }.apply {
                assertEquals(
                    """
                    {
                      "data": {
                        "deleteUser": "9"
                      }
                    }
                """.trimIndent(), response.content
                )
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
