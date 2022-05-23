package com.diploma.databaseMutationController

import com.diploma.model.Readings
import com.diploma.model.ReadingsDataInput
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
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

}