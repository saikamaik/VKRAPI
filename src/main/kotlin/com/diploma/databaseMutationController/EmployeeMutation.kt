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

    fun updateEmployee(id: Int, name: String?, phoneNumber: String?, description: String?, branchId: Int?, positionId: Int?) {
        transaction {
            Employee.update({Employee.id eq id}) {
                if (name != null) it[Employee.name] = name
                if (phoneNumber != null) it[Employee.phoneNumber] = phoneNumber
                if (description != null) it[Employee.description] = description
                if (branchId != null) it[Employee.branchId] = branchId
                if (positionId != null) it[Employee.positionId] = positionId
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