package com.diploma.databaseMutationController

import com.diploma.model.Category
import com.diploma.model.CategoryData
import com.diploma.model.CategoryDataInput
import com.diploma.model.Readings
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryMutation {

    fun createCategory(data: CategoryDataInput) {
        transaction {
            Category.insert {
                it[name] = data.name!!
            }
        }
    }

    fun updateCategory(id: Int, data: CategoryDataInput) {
        transaction {
            Category.update({ Category.id eq id }) {
                it[name] = data.name!!
            }
        }
    }

    fun deleteCategory(id: Int) {
        transaction {
            Category.deleteWhere { Category.id eq id }
        }
    }

    fun showCategory(id: Int?, name: String?): List<CategoryData> {
        return when {
            (id != null && name != null) -> {
                Category
                    .select { (Category.id eq id) and (Category.name eq name) }
                    .map { Category.toMap(it) }
            }
            id != null -> {
                Category.select { Category.id eq id }.map { Category.toMap(it) }
            }
            name != null -> {
                Category.select { Category.name eq name }.map { Category.toMap(it)}
            }
            else -> Category.selectAll().map { Category.toMap(it) }
        }
    }
}