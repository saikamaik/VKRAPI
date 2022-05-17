package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class PositionData(
    var id: Int? = null,
    var name: String = ""
)

data class PositionDataInput(
    var name: String = ""
)

object Position: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id, name = "position_pk")

    fun toMap(row: ResultRow): PositionData =
        PositionData(
            id = row[id],
            name = row[name]
        )

}
