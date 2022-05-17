package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class EmployeeData(
    var id: Int? = null,
    var name: String,
    var phoneNumber: String,
    var description: String,
    var branchId: Int,
    var positionId: Int
)

data class EmployeeDataInput(
    val name: String? = "",
    val phoneNumber: String? = "",
    val description: String? = "",
    val branchId: Int?,
    val positionId: Int?
)

object Employee: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val phoneNumber = varchar("phone_number", 255)
    val description = varchar("description", 255)
    val branchId = integer("branch_id") references Branch.id
    val positionId = integer("position_id") references Position.id

    override val primaryKey = PrimaryKey(id, name = "employee_pk")

    fun toMap(row: ResultRow): EmployeeData =
        EmployeeData(
            id = row[id],
            name = row[name],
            phoneNumber = row[phoneNumber],
            description = row[description],
            branchId = row[branchId],
            positionId = row[positionId]
        )
}