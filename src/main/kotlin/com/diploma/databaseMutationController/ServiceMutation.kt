package com.diploma.databaseMutationController

import com.diploma.model.Service
import com.diploma.model.ServiceData
import com.diploma.model.ServiceDataInput
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ServiceMutation {

    fun createService(data: ServiceDataInput) {
        transaction {
            Service.insert {
                it[name] = data.name
                it[customWork] = data.customWork
                it[description] = data.description
                it[cost] = data.cost
                it[positionId] = data.positionId
                it[measureRefId] = data.measureRefId
            }
        }
    }

    fun updateService(id: Int, data: ServiceDataInput) {
        transaction {
            Service.update ({Service.id eq id}) {
                it[name] = data.name
                it[customWork] = data.customWork
                it[description] = data.description
                it[cost] = data.cost
                it[positionId] = data.positionId
                it[measureRefId] = data.measureRefId
            }
        }
    }

    fun deleteService(id: Int) {
        transaction {
            Service.deleteWhere { Service.id eq id }
        }
    }

}