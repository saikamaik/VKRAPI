package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class BranchData(
    var id: Int? = null,
    var name: String? = "",
    var country: String? = "",
    var city: String? = "",
    var address: String? = "",
    var phoneNumber: String? = "",
    var orgId: Int?
)

data class BranchDataInput(
    val name: String? = "",
    val country: String? = "",
    val city: String? = "",
    val address: String? = "",
    val phoneNumber: String? = "",
    val orgId: Int?
)

object Branch: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val country = varchar("country", 255)
    val city = varchar("city", 255)
    val address = varchar("address", 255)
    val phoneNumber = varchar("phone_number", 255)
    val orgId = integer("org_id") references Organization.id

    override val primaryKey = PrimaryKey(id, name = "branch_pk")

    fun toMap(row: ResultRow): BranchData =
        BranchData(
            id = row[id],
            name = row[name],
            country = row[country],
            city = row[city],
            address = row[address],
            phoneNumber = row[phoneNumber],
            orgId = row[orgId]
        )
}