package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

data class ReadingsData(
    val id: Int,
    val reading: Float,
    val date: String,
    val apartmentId: Int,
    val counterRefId: Int
)

data class ReadingsDataInput(
    val reading: Float?,
    val date: String?,
    val apartmentId: Int?,
    val counterRefId: Int?
)

object Readings: Table() {
    val id = integer("id").autoIncrement()
    val reading = float("reading")
    val date = datetime("date")
    val apartmentId = integer("apartment_id") references Apartment.id
    val counterRefId = integer("cf_id") references Counter_Reference.id

    override val primaryKey = PrimaryKey(id, name = "readings_pk")

    fun toMap(row: ResultRow): ReadingsData =
        ReadingsData(
            id = row[id],
            reading = row[reading],
            date = row[date].toString("dd-MM-yyyy"),
            apartmentId = row[apartmentId],
            counterRefId = row[counterRefId]
        )
}

