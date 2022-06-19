package com.diploma.databaseMutationController

import com.diploma.model.Branch
import com.diploma.model.BranchData
import com.diploma.model.BranchDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class BranchMutation {

    fun createBranch(data: BranchDataInput) {
        transaction {
            Branch.insert {
                it[name] = data.name!!
                if (data.country != null) it[country] = data.country
                if (data.city != null) it[city] = data.city
                it[address] = data.address!!
                it[phoneNumber] = data.phoneNumber!!
                it[orgId] = data.orgId!!
            }
        }
    }

    fun updateBranch(
        id: Int,
        name: String?,
        country: String?,
        city: String?,
        address: String?,
        phoneNumber: String?,
        orgId: Int?
    ) {
        transaction {
            Branch.update({ Branch.id eq id }) {
                if (name != null) it[Branch.name] = name
                if (country != null) it[Branch.country] = country
                if (city != null) it[Branch.city] = city
                if (address != null) it[Branch.address] = address
                if (phoneNumber != null) it[Branch.phoneNumber] = phoneNumber
                if (orgId != null) it[Branch.orgId] = orgId
            }
        }
    }

    fun deleteBranch(data: Int) {
        transaction {
            Branch.deleteWhere { Branch.id eq data }
        }
    }

    //service collection inner key

    fun showBranch(
        id: Int?,
        name: String?,
        country: String?,
        city: String?,
        address: String?,
        orgId: Int?
    ): List<BranchData> {
        return when {
            id != null -> {
                Branch.select { Branch.id eq id }.map { Branch.toMap(it) }
            }
            name != null -> {
                Branch.select { Branch.name eq name }.map { Branch.toMap(it) }
            }
            country != null -> {
                Branch.select { Branch.country eq country }.map { Branch.toMap(it) }
            }
            city != null -> {
                Branch.select { Branch.city eq city }.map { Branch.toMap(it) }
            }
            address != null -> {
                Branch.select { Branch.address eq address }.map { Branch.toMap(it) }
            }
            orgId != null -> {
                Branch.select { Branch.orgId eq orgId }.map { Branch.toMap(it) }
            }
            else -> Branch.selectAll().map { Branch.toMap(it) }
        }
    }

}