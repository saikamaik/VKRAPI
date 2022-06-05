package com.diploma.databaseMutationController

import com.diploma.model.Readings
import com.diploma.model.ReadingsData
import com.diploma.model.ReadingsDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class ReadingsMutation {

    fun createReadings(data: ReadingsDataInput) {
        transaction {
            Readings.insert {
                it[reading] = data.reading!!
                it[date] = DateTime(data.date)
                it[apartmentId] = data.apartmentId!!
                it[counterRefId] = data.counterRefId!!
            }
        }
    }

    fun updateReadings(id: Int, data: ReadingsDataInput) {
        transaction {
            Readings.update ({ Readings.id eq id}) {
                if(data.reading != null) it[reading] = data.reading
                if(data.date != null) it[date] = DateTime(data.date)
                if(data.apartmentId != null) it[apartmentId] = data.apartmentId
                if(data.counterRefId != null) it[counterRefId] = data.counterRefId
            }
        }
    }

    fun deleteReadings(id: Int) {
        transaction {
            Readings.deleteWhere { Readings.id eq id }
        }
    }

    fun showReadings(id: Int?, apartmentId: Int?, counterRefId: Int?): List<ReadingsData> {
        return when {
            id != null -> {
                Readings.select { Readings.id eq id }.map { Readings.toMap(it) }
            }
            apartmentId != null -> {
                Readings.select { Readings.apartmentId eq apartmentId }.map { Readings.toMap(it) }
            }
            counterRefId != null -> {
                Readings.select { Readings.counterRefId eq counterRefId }.map { Readings.toMap(it) }
            }
            else -> Readings.selectAll().map { Readings.toMap(it) }
        }
    }
}