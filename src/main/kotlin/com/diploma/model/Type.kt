package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class TypeData(
    var id: Int?,
    var name: String
)

data class TypeDataInput(
    var name: String
)

object Type: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id, name = "type_pk")

    fun toMap(row: ResultRow): TypeData =
        TypeData(
            id = row[id],
            name = row[name]
        )
}
