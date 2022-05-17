package com.diploma.model

import com.diploma.model.Position.autoIncrement
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class MeasureReferenceData(
    var id: Int,
    var fullName: String,
    var shortName: String
)

data class MeasureReferenceDataInput(
    var fullName: String = "",
    var shortName: String = ""
)

object MeasureReference: Table() {
    val id = integer("id").autoIncrement()
    val fullName = varchar("full_name", 255)
    val shortName = varchar("short_name", 255)

    override val primaryKey = PrimaryKey(id, name = "measure_reference_pk")

    fun toMap(row: ResultRow): MeasureReferenceData =
        MeasureReferenceData(
            id = row[id],
            fullName = row[fullName],
            shortName = row[shortName]
        )
}
