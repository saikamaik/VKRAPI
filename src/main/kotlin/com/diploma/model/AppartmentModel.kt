package com.diploma.model

data class ApartmentData(
    var id: Int? = null,
    var fullSize: Int,
    var liveSize: Int,
    var category: String,
    var brunchId: Int,
    var personalAccount: Int
)

data class ApartmentDataInput(
    var fullSize: Int,
    var liveSize: Int,
    var category: String,
    var brunchId: Int,
    var personalAccount: Int
)