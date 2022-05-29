package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class CounterReferenceData(
    var id: Int,
    var number: String,
    var model: String,
    var label: String,
    var serviceDate: String,
    var typeId: Int
)

data class CounterReferenceDataInput(
    val number: String? = "",
    val model: String? = "",
    val label: String? = "",
    val serviceDate: String? = "",
    val typeId: Int?
)

object Counter_Reference: Table() {
    val id = integer("id").autoIncrement()
    val number = varchar("number", 255)
    val model = varchar("model", 255)
    val label = varchar("label", 255)
    val serviceDate = varchar("service_date", 255)
    val typeId = integer("type_id") references Type.id

    override val primaryKey = PrimaryKey(id, name = "counter_reference_pk")

    fun toMap(row: ResultRow): CounterReferenceData =
        CounterReferenceData(
            id = row[id],
            number = row[number],
            model = row[model],
            label = row[label],
            serviceDate = row[serviceDate],
            typeId = row[typeId]
        )
}