package com.diploma.model

data class GraphQLRequest(
    val query: String,
    var variables: Map<String, Any>? = null
)


