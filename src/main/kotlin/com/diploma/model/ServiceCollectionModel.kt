package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class ServiceCollectionData(
    var id: Int?,
    var branchId: Int?,
    var serviceId: Int?,
    var cost: Float?
)

data class ServiceCollectionDataInput(
    var branchId: Int?,
    var serviceId: Int?,
    var cost: Float?
)

object Service_Collection: Table() {
    val id = integer("id").autoIncrement()
    val branchId = integer("branch_id") references Branch.id
    val serviceId = integer("service_id") references Service.id
    val cost = float("cost")

    override val primaryKey = PrimaryKey (id, name = "service_collection_pk")

    fun toMap(row: ResultRow): ServiceCollectionData =
        ServiceCollectionData(
            id = row[id],
            branchId = row[branchId],
            serviceId = row[serviceId],
            cost = row[cost]
        )
}