package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class OrganizationData(
    var id: Int?,
    var name: String?
)

data class OrganizationDataInput(
    var name: String = ""
)

object Organization: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id, name = "organization_pk")

    fun toMap(row: ResultRow): OrganizationData =
        OrganizationData(
            id = row[id],
            name = row[name]
        )
}
