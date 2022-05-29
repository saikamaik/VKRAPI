package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class ServiceData(
    val id: Int,
    val name: String,
    val customWork: Boolean,
    val description: String,
    val cost: Float,
    val positionId: Int,
    val measureRefId: Int
)

data class ServiceDataInput(
    val name: String,
    val customWork: Boolean,
    val description: String,
    val cost: Float,
    val positionId: Int,
    val measureRefId: Int
)

object Service: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val customWork = bool("custom_work")
    val description = varchar("description", 600)
    val cost = float("cost")
    val positionId = integer("position_id") references Position.id
    val measureRefId = integer("mr_id") references Measure_Reference.id

    override val primaryKey = PrimaryKey (id, name = "service_pk")

    fun toMap(row: ResultRow): ServiceData =
        ServiceData(
            id = row[id],
            name = row[name],
            customWork = row[customWork],
            description = row[description],
            cost = row[cost],
            positionId = row[positionId],
            measureRefId = row[measureRefId]
        )
}