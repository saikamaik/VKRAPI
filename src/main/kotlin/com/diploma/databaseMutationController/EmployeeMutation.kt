package com.diploma.databaseMutationController

import com.diploma.model.Employee
import com.diploma.model.EmployeeData
import com.diploma.model.EmployeeDataInput
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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

}