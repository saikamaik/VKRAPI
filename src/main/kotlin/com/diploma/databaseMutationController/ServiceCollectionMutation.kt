package com.diploma.databaseMutationController

import com.diploma.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.cos

class ServiceCollectionMutation {

    fun createServiceCollection(data: ServiceCollectionDataInput) {
        transaction {
            Service_Collection.insert {
                it[branchId]  = data.branchId!!
                it[serviceId] = data.serviceId!!
                it[cost] = data.cost!!
            }
        }
    }

    fun updateServiceCollection(id: Int, data: ServiceCollectionDataInput) {
        transaction {
            Service_Collection.update ({ Service_Collection.id eq id}) {
                if(data.branchId != null) it[branchId] = data.branchId!!
                if(data.serviceId != null) it[serviceId] = data.serviceId!!
                if(data.cost != null) it[cost] = data.cost!!
            }
        }
    }

    fun deleteServiceCollection(id: Int) {
        transaction {
            Service_Collection.deleteWhere { Service_Collection.id eq id }
        }
    }

    fun showServiceCollection(branchId: Int?, serviceId: Int?, cost1: Float?, cost2: Float?): List<ServiceCollectionData> {
        return when {
            ((branchId != null) && (serviceId != null) && (cost1 != null) && (cost2 != null )) -> {
                Service_Collection
                    .select {(Service_Collection.branchId eq branchId) and
                            (Service_Collection.serviceId eq serviceId) and
                            (Service_Collection.cost.greaterEq(cost1)) and
                            (Service_Collection.cost.lessEq(cost2)) }
                    .map { Service_Collection.toMap(it) }
            }
            ((branchId != null) && (serviceId != null) && (cost1 != null)) -> {
                Service_Collection
                    .select {(Service_Collection.branchId eq branchId) and
                            (Service_Collection.serviceId eq serviceId) and
                            (Service_Collection.cost.greaterEq(cost1))}
                    .map { Service_Collection.toMap(it) }
            }
            ((branchId != null) && (serviceId != null) && (cost2 != null )) -> {
                Service_Collection
                    .select {(Service_Collection.branchId eq branchId) and
                            (Service_Collection.serviceId eq serviceId) and
                            (Service_Collection.cost.lessEq(cost2))}
                    .map { Service_Collection.toMap(it) }
            }
            ((branchId != null) && (serviceId != null)) -> {
                Service_Collection
                    .select {(Service_Collection.branchId eq branchId) and (Service_Collection.serviceId eq serviceId)}
                    .map { Service_Collection.toMap(it) }
            }
            (branchId != null) -> {
                Service_Collection
                    .select {Service_Collection.branchId eq branchId}
                    .map { Service_Collection.toMap(it) }
            }
            (serviceId != null) -> {
                Service_Collection
                    .select {Service_Collection.serviceId eq serviceId}
                    .map { Service_Collection.toMap(it) }
            }
            ((cost1 != null) && (cost2 != null )) -> {
                Service_Collection
                    .select { (Service_Collection.cost.greaterEq(cost1)) and
                            (Service_Collection.cost.lessEq(cost2)) }
                    .map { Service_Collection.toMap(it) }
            }
            (cost1 != null) -> {
                Service_Collection
                    .select { (Service_Collection.cost.greaterEq(cost1)) }
                    .map { Service_Collection.toMap(it) }
            }
            (cost2 != null) -> {
                Service_Collection
                    .select { (Service_Collection.cost.lessEq(cost2)) }
                    .map { Service_Collection.toMap(it) }
            }
            else -> Service_Collection.selectAll().map { Service_Collection.toMap(it) }
        }
    }

}