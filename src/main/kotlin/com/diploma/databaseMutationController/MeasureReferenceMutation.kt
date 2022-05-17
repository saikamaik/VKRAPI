package com.diploma.databaseMutationController

import com.diploma.model.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class MeasureReferenceMutation {

    fun createMeasureRef(data: MeasureReferenceDataInput) {
        transaction {
            MeasureReference.insert {
                it[fullName] = data.fullName
                it[shortName] = data.shortName
            }
        }
    }

    fun updateMeasureRef(data: MeasureReferenceData) {
        transaction {
                MeasureReference.update({ MeasureReference.id eq data.id}) {
                    it[fullName] = data.fullName
                    it[shortName] = data.shortName
                }
            }
        }

    fun deleteMeasureRef(id: Int) {
        transaction {
            if (checkForInnerKey(id)) {
                return@transaction
            } else {
                MeasureReference.deleteWhere { MeasureReference.id eq id }
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