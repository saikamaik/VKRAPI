package com.diploma.databaseMutationController

import com.diploma.model.MeasureReferenceData
import com.diploma.model.ServiceRecordData
import com.diploma.model.Service_Record
import com.diploma.model.ServiceRecordDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class ServiceRecordMutation {

    fun createServiceRecord(data: ServiceRecordDataInput) {
        transaction {
            Service_Record.insert {
                it[registrationDate] = DateTime(data.registrationDate)
                it[status] = data.status
                it[userId] = data.userId
                it[serviceId] = data.serviceId
                it[employeeId] = data.employeeId
            }
        }
    }

    fun updateServiceRecord(id: Int, data: ServiceRecordDataInput) {
        transaction {
            Service_Record.update ({ Service_Record.id eq id}) {
                it[registrationDate] = DateTime(data.registrationDate)
                it[status] = data.status
                it[userId] = data.userId
                it[serviceId] = data.serviceId
                it[employeeId] = data.employeeId
            }
        }
    }

    fun deleteServiceRecord(id: Int) {
        transaction {
            Service_Record.deleteWhere { Service_Record.id eq id }
        }
    }

    fun showServiceRecord(id: Int?, registrationDate: String?, status: String?, userId: Int?, serviceId: Int?, employeeId: Int?): List<ServiceRecordData> {
        val date = DateTime(registrationDate)
        return when {
            id != null -> {
                Service_Record.select { Service_Record.id eq id }. map { Service_Record.toMap(it) }
            }
            registrationDate != null -> {
                Service_Record.select { Service_Record.registrationDate eq date }. map { Service_Record.toMap(it) }
            }
            status != null -> {
                Service_Record.select { Service_Record.status eq status }. map { Service_Record.toMap(it) }
            }
            userId != null -> {
                Service_Record.select { Service_Record.userId eq userId }. map { Service_Record.toMap(it) }
            }
            serviceId != null -> {
                Service_Record.select { Service_Record.serviceId eq serviceId }. map { Service_Record.toMap(it) }
            }
            employeeId != null -> {
                Service_Record.select { Service_Record.employeeId eq employeeId }. map { Service_Record.toMap(it) }
            }
            else -> Service_Record.selectAll().map { Service_Record.toMap(it) }
        }
    }

}