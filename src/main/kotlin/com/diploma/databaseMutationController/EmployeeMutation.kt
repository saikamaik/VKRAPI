package com.diploma.databaseMutationController

import com.diploma.model.Employee
import com.diploma.model.EmployeeData
import com.diploma.model.EmployeeDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class EmployeeMutation {

    fun createEmployee(data: EmployeeDataInput) {
        transaction {
            Employee.insert {
                it[name] = data.name!!
                it[phoneNumber] = data.phoneNumber!!
                it[description] = data.description!!
                it[branchId] = data.branchId!!
                it[positionId] = data.positionId!!
            }[Employee.id]
        }
    }

    fun updateEmployee(id: Int, data: EmployeeDataInput) {
        transaction {
            Employee.update({Employee.id eq id}) {
                if (data.name != null) it[name] = data.name
                if (data.phoneNumber != null) it[phoneNumber] = data.phoneNumber
                if (data.description != null) it[description] = data.description
                if (data.branchId != null) it[branchId] = data.branchId
                if (data.positionId != null) it[positionId] = data.positionId
            }
        }
    }

    fun deleteEmployee(id: Int) {
        transaction {
            Employee.deleteWhere { Employee.id eq id }
        }
    }

    fun showEmployee(id: Int?, name: String?, branchId: Int?, positionId: Int?): List<EmployeeData> {
        return when {
            id != null -> {
                Employee.select { Employee.id eq id }.map { Employee.toMap(it) }
            }
            name != null -> {
                Employee.select { Employee.name eq name }.map { Employee.toMap(it) }
            }
            branchId != null -> {
                Employee.select { Employee.branchId eq branchId }.map { Employee.toMap(it) }
            }
            positionId != null ->{
                Employee.select { Employee.positionId eq positionId }.map { Employee.toMap(it) }
            }
            else -> Employee.selectAll().map { Employee.toMap(it) }
        }
    }
}