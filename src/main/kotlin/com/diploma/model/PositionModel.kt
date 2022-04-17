package com.diploma.model

import com.diploma.model.Organization.autoIncrement
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
    private val id = integer("id").autoIncrement()
    private val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id, name = "position_pkey")

    fun toMap(row: ResultRow): PositionData =
        PositionData(
            id = row[id],
            name = row[name]
        )

}
