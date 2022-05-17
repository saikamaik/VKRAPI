package com.diploma.model

import org.jetbrains.exposed.sql.Table

data class ServiceCollectionData(
    var branchId: Int,
    var serviceId: Int,
    var cost: Float
)

object ServiceCollection: Table() {
    val branchId = integer("branch_id") references Branch.id
    val serviceId = integer("service_id") references Service.id
    val cost = float("cost")

}