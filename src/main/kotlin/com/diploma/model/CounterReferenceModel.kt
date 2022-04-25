package com.diploma.model

import java.sql.Date

data class CounterReferenceData(
    var id: Int,
    var number: String,
    var model: String,
    var label: String,
    var serviceDate: String,
    var typeId: Int
)

data class CounterReferenceDataInput(
    val number: String? = "",
    val model: String? = "",
    val label: String? = "",
    val serviceDate: String? = "",
    val typeId: Int?
)