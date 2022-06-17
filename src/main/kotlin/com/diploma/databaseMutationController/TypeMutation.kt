package com.diploma.databaseMutationController

import com.diploma.model.Counter_Reference
import com.diploma.model.Type
import com.diploma.model.TypeData
import com.diploma.model.TypeDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TypeMutation {

    fun createType(data: TypeDataInput) {
        transaction {
            Type.insert {
                it[name] = data.name
            }
        }
    }

    fun updateType(id: Int, name: String?) {
        transaction {
            Type.update({Type.id eq id}) {
               if (name != null) it[Type.name] = name
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

    fun showType(id: Int?, name: String?): List<TypeData> {
        return when {
            (id != null && name != null) ->
                Type
                    .select{ (Type.id eq id) and (Type.name eq name)}
                    .map { Type.toMap(it) }
            id != null ->
                Type.select { Type.id eq id }.map { Type.toMap(it) }
            name != null ->
                Type.select { Type.name eq name }.map {Type.toMap(it)}
            else -> Type.selectAll().map { Type.toMap(it) }
        }
    }
}