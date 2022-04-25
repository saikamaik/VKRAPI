package com.diploma.model

data class MeasureReferenceData(
    var id: Int,
    var fullName: String,
    var shortName: String
)

data class MeasureReferenceDataInput(
    var fullName: String? = "",
    var shortName: String? = ""
)
