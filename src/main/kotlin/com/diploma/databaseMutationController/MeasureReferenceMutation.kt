package com.diploma.databaseMutationController

import com.diploma.model.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class MeasureReferenceMutation {

    fun createMeasureReference(data: MeasureReferenceDataInput) {
        transaction {
            Measure_Reference.insert {
                it[fullName] = data.fullName!!
                it[shortName] = data.shortName!!
            }
        }
    }

    fun updateMeasureReference(id: Int, data: MeasureReferenceDataInput) {
        transaction {
                Measure_Reference.update({ Measure_Reference.id eq id}) {
                    it[fullName] = data.fullName!!
                    it[shortName] = data.shortName!!
                }
            }
        }

    fun deleteMeasureReference(id: Int) {
        transaction {
            if (checkForInnerKey(id)) {
                return@transaction
            } else {
                Measure_Reference.deleteWhere { Measure_Reference.id eq id }
            }
        }
    }

    private fun checkForInnerKey(id: Int): Boolean {
        var eq = true
        Service.selectAll().forEach() { _ ->
            eq = Service.measureRefId.equals(id)
        }
        return eq
    }

}