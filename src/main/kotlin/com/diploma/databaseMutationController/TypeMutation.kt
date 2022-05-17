package com.diploma.databaseMutationController

import com.diploma.model.CounterReference
import com.diploma.model.Type
import com.diploma.model.TypeData
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TypeMutation {

    fun createType(data: TypeData) {
        transaction {
            Type.insert {
                it[name] = data.name
            }
        }
    }

    fun updateType(data: TypeData) {
        transaction {
            Type.update({Type.id eq data.id!!}) {
                it[name] = data.name
            }
        }
    }

    fun deleteType(id: Int) {
        transaction {
            if (checkForInnerKey(id)) {
                return@transaction
            } else {
                Type.deleteWhere { Type.id eq id }
            }
        }
    }

    private fun checkForInnerKey(id: Int): Boolean {
        var eq = true
        CounterReference.selectAll().forEach() { _ ->
            eq = CounterReference.typeId.equals(id)
        }
        return eq
    }
}