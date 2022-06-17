package com.diploma.databaseMutationController

import com.diploma.model.Service
import com.diploma.model.ServiceData
import com.diploma.model.ServiceDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ServiceMutation {

    fun createService(data: ServiceDataInput) {
        transaction {
            Service.insert {
                it[name] = data.name!!
                it[customWork] = data.customWork!!
                it[description] = data.description!!
                it[positionId] = data.positionId!!
                it[measureRefId] = data.measureRefId!!
                it[categoryId] = data.categoryId!!
            }
        }
    }

    fun updateService(id: Int, name: String?, customWork: Boolean?, description: String?, positionId: Int?, measureRefId: Int?) {
        transaction {
            Service.update ({Service.id eq id}) {
                if(name != null) it[Service.name] = name
                if(customWork != null) it[Service.customWork] = customWork
                if(description != null) it[Service.description] = description
                if(positionId != null) it[Service.positionId] = positionId
                if(measureRefId != null) it[Service.measureRefId] = measureRefId
            }
        }
    }

    fun deleteService(id: Int) {
        transaction {
            Service.deleteWhere { Service.id eq id }
        }
    }

    fun showService(name: String?, serviceId: Int?, categoryId: Int?, positionId: Int? ): List<ServiceData> {
        return when {
            (name != null && serviceId != null && categoryId != null && positionId != null) -> {
                Service
                    .select { (Service.name eq name) and
                            (Service.id eq serviceId) and
                            (Service.categoryId eq categoryId) and
                            (Service.positionId eq positionId) }
                    .map {Service.toMap(it)}
            }
            (name != null) -> {
                Service.select { Service.name eq name }.map {Service.toMap(it)}
            }
            (serviceId != null) -> {
                Service.select { Service.id eq serviceId }.map { Service.toMap(it) }
            }
            (categoryId != null) -> {
                Service.select { Service.categoryId eq categoryId }.map { Service.toMap(it) }
            }
            (positionId != null) -> {
                Service.select {Service.positionId eq positionId}.map { Service.toMap(it) }
            }
            else -> Service.selectAll().map { Service.toMap(it) }
        }
    }

}