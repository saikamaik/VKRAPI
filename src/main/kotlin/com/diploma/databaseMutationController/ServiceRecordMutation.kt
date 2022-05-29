package com.diploma.databaseMutationController

import com.diploma.model.Service_Record
import com.diploma.model.ServiceRecordDataInput
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
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

}