package com.diploma.databaseMutationController

import com.diploma.model.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class OrganizationMutation {

    fun createOrg(data: OrganizationDataInput) {
        transaction {
            Organization.insert {
                it[name] = data.name
            }[Organization.id]
        }
    }

    fun updateOrg(data: OrganizationData) {
        transaction {
            Organization.update({ Organization.id eq data.id!! }) {
                it[name] = data.name!!
            }
        }
    }

    fun deleteOrg(data: Int) {
        transaction {
            if (checkForInnerKey(data)) {
                return@transaction
            } else Organization.deleteWhere { Organization.id eq data }
        }
    }

    private fun checkForInnerKey(id: Int): Boolean {
        var eq = true
        User.selectAll().forEach() { _ ->
            eq = User.orgId.equals(id)
        }
        return eq
    }

}