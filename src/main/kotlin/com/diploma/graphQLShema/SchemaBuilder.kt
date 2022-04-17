package com.diploma.graphQLShema

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.diploma.databaseMutationController.OrganizationMutation
import com.diploma.databaseMutationController.UserMutation
import com.diploma.init
import com.diploma.model.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

fun SchemaBuilder.schemaValue() {

    init()

    mutation("createUser") {
        description = "Create a new user"
        resolver { userInput: UserDataInput ->
            try {
                UserMutation().createUser(userInput)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("updateUser") {
        description = "Update user"
        resolver { id: Int, userInput: UserDataInput ->
            try{
                UserMutation().updateUser(id, userInput)
            true
        } catch (e: Exception) {
        false
    } }
    }

    mutation("deleteUser") {
        description = "Delete a user by his identifier"
        resolver { id: Int ->
            try {
                UserMutation().deleteUser(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllUsers") {
        description = "Показывает всех пользователей"
        resolver { ->
            transaction {
                User.selectAll().map{ User.toShowMap(it)}
            }
        }
    }

    query("showUserInfo") {
        description = "Показывает инфу текущего пользователя"
        resolver { id: Int ->
            transaction {
                User.select { User.id eq id }.map { User.toShowMap(it)}
            }
        }
    }

    mutation("createOrg") {
        description = "Create a new org"
        resolver { orgInput: OrganizationInputData ->
            try {
                OrganizationMutation().createOrg(orgInput)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    mutation("deleteOrg") {
        description = "Delete org by it identifier"
        resolver { id: Int ->
            try {
                OrganizationMutation().deleteOrg(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    query("showAllOrg") {
        description = "Показывает всех org"
        resolver { ->
            transaction {
                Organization.selectAll().map{ Organization.toMap(it)}
            }
        }
    }

    type<OrganizationData>{
        description = "Org Data"
    }

    type<UserData>{
        description = "dflkgnjhlkdfng"
    }

    inputType<UserDataInput>{
        description = "The input of the users without the identifier"
    }

    inputType<OrganizationInputData>{
        description = "The input of the orgs without the identifier"
    }

}


