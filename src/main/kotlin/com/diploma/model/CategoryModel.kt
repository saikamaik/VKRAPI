package com.diploma.model

import com.diploma.model.Service.autoIncrement
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class CategoryData(
    val id: Int?,
    val name: String?
)

data class CategoryDataInput(
    val name: String?
)

object Category: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey (Service.id, name = "category_id")

    fun toMap(row: ResultRow): CategoryData =
        CategoryData(
            id = row[id],
            name = row[name]
        )
}
