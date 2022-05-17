package com.diploma.databaseMutationController

import com.diploma.model.Branch
import com.diploma.model.BranchData
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class BranchMutation {

    fun createBranch(data: BranchData) {
        transaction {
            Branch.insert {
                it[name] = data.name!!
                it[country] = data.country!!
                it[city] = data.city!!
                it[address] = data.address!!
                it[phoneNumber] = data.phoneNumber!!
                it[orgId] = data.orgId!!
            }
        }
    }

    fun updateBranch(id: Int, data: BranchData) {
        transaction {
            Branch.update({Branch.id eq id}) {
                if (data.name!= null) it[name] = data.name!!
                if (data.country!= null) it[country] = data.country!!
                if (data.city!= null) it[city] = data.city!!
                if (data.address!= null) it[address] = data.address!!
                if (data.phoneNumber!= null) it[phoneNumber] = data.phoneNumber!!
                if (data.orgId!= null) it[orgId] = data.orgId!!
            }
        }
    }

    fun deleteBranch(data: Int) {
        transaction {
            Branch.deleteWhere { Branch.id eq data }
        }
    }

    //service collection inner key

}