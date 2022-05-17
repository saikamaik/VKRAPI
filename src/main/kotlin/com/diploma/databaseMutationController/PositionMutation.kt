package com.diploma.databaseMutationController

import com.diploma.model.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class PositionMutation {

    fun createPosition(data: PositionData) {
        transaction {
            Position.insert {
                it[name] = data.name
            }[Position.id]
        }
    }

    fun updatePosition(id: Int, data: PositionData) {
        transaction {
            Position.update({Position.id eq id}) {
                it[name] = data.name
            }
        }
    }

    fun deletePosition(data: Int) {
        transaction {
            if (checkForInnerKey(data)) {
                return@transaction
            } else Position.deleteWhere { Position.id eq data }
        }
    }

    private fun checkForInnerKey(id: Int): Boolean {
        var eq1 = true
        var eq2 = true
        Employee.selectAll().forEach() { _ ->
            eq1 = Employee.positionId.equals(id)
        }
        Service.selectAll().forEach() {_ ->
            eq2 = Service.positionId.equals(id)
        }
        return (eq1) or (eq2)
    }

}