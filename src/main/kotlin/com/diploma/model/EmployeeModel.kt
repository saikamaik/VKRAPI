package com.diploma.model

data class EmployeeData(
    var id: Int? = null,
    var name: String,
    var phoneNumber: String,
    var description: String,
    var branchId: Int,
    var positionId: Int
)