package com.diploma.databaseMutationController

import com.diploma.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OrganizationMutation {

    fun createOrg(data: OrganizationDataInput) {
        transaction {
            Organization.insert {
                it[name] = data.name
            }[Organization.id]
        }
    }

    fun updateOrg(id: Int, name: String?) {
        transaction {
            Organization.update({ Organization.id eq id }) {
                if (name!=null) it[Organization.name] = name
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

    fun showOrg(id: Int?, name: String?): List<OrganizationData> {
        return when {
            (id != null && name != null) ->
                Organization
                    .select {(Organization.id eq id) and (Organization.name eq name)}
                    .map { Organization.toMap(it) }
            id != null ->
                Organization.select {Organization.id eq id}.map { Organization.toMap(it) }
            name != null ->
                Organization.select {Organization.name eq name}.map { Organization.toMap(it) }
            else -> Organization.selectAll().map { Organization.toMap(it) }
        }


    }

}