package com.diploma.model

data class BranchData(
    var id: Int = 1,
    var name: String = "",
    var country: String = "",
    var city: String = "",
    var address: String = "",
    var phoneNumber: String = "",
    var orgId: Int
)

data class BranchDataInput(
    var name: String = "",
    var country: String = "",
    var city: String = "",
    var address: String = "",
    var phoneNumber: String = "",
    var orgId: Int
)