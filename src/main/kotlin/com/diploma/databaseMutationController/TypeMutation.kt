package com.diploma.databaseMutationController

import com.diploma.model.Counter_Reference
import com.diploma.model.Type
import com.diploma.model.TypeDataInput
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TypeMutation {

    fun createType(data: TypeDataInput) {
        transaction {
            Type.insert {
                it[name] = data.name
            }
        }
    }

    fun updateType(id: Int, data: TypeDataInput) {
        transaction {
            Type.update({Type.id eq id}) {
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
        Counter_Reference.selectAll().forEach() { _ ->
            eq = Counter_Reference.typeId.equals(id)
        }
        return eq
    }
}