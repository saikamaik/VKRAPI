package com.diploma.databaseMutationController

import com.diploma.model.ServiceRecord
import com.diploma.model.ServiceRecordDataInput
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime

class ServiceRecordMutation {

    fun createServiceRecord(data: ServiceRecordDataInput) {
        transaction {
            ServiceRecord.insert {
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
            ServiceRecord.update ({ ServiceRecord.id eq id}) {
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
            ServiceRecord.deleteWhere { ServiceRecord.id eq id }
        }
    }

}