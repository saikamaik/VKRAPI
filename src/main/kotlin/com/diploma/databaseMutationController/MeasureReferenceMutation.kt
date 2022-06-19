package com.diploma.databaseMutationController

import com.diploma.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MeasureReferenceMutation {

    fun createMeasureReference(data: MeasureReferenceDataInput) {
        transaction {
            Measure_Reference.insert {
                it[fullName] = data.fullName!!
                it[shortName] = data.shortName!!
            }
        }
    }

    fun updateMeasureReference(id: Int, fullName: String?, shortName: String?) {
        transaction {
            Measure_Reference.update({ Measure_Reference.id eq id }) {
                if (fullName != null) it[Measure_Reference.fullName] = fullName
                if (shortName != null) it[Measure_Reference.shortName] = shortName
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

    fun showMeasureReference(id: Int?, fullName: String?, shortName: String?): List<MeasureReferenceData> {
        return when {
            (id != null && fullName != null && shortName != null) ->
                Measure_Reference
                    .select {
                        (Measure_Reference.id eq id) and
                                (Measure_Reference.fullName eq fullName) and
                                (Measure_Reference.shortName eq shortName)
                    }.map { Measure_Reference.toMap(it) }
            id != null -> {
                Measure_Reference.select { Measure_Reference.id eq id }.map { Measure_Reference.toMap(it) }
            }
            fullName != null -> {
                Measure_Reference.select { Measure_Reference.fullName eq fullName }.map { Measure_Reference.toMap(it) }
            }
            shortName != null -> {
                Measure_Reference.select { Measure_Reference.shortName eq shortName }
                    .map { Measure_Reference.toMap(it) }
            }
            else -> Measure_Reference.selectAll().map { Measure_Reference.toMap(it) }
        }
    }

}