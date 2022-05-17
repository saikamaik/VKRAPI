package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

data class ServiceRecordData(
    val id: Int,
    val registrationDate: String,
    val status: String,
    val userId: Int,
    val serviceId: Int,
    val employeeId: Int
)

data class ServiceRecordDataInput(
    val registrationDate: String,
    val status: String,
    val userId: Int,
    val serviceId: Int,
    val employeeId: Int
)

object ServiceRecord: Table() {
    val id = integer("id").autoIncrement()
    val registrationDate = datetime("registration_date")
    val status = varchar("status", 255)
    val userId = integer("user_id") references User.id
    val serviceId = integer("service_id") references Service.id
    val employeeId = integer("emp_id") references Employee.id

    override val primaryKey = PrimaryKey(id, name = "service_record_pk")

    fun toMap(row: ResultRow): ServiceRecordData =
        ServiceRecordData (
            id = row[id],
            registrationDate = row[registrationDate].toString("dd-MM-yyyy"),
            status = row[status],
            userId = row[userId],
            serviceId = row[serviceId],
            employeeId = row[employeeId]
                )
}
