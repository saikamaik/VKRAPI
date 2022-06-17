package com.diploma.model

import com.diploma.model.User.autoIncrement
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class ApartmentData(
    var id: Int? = null,
    var fullSize: Float,
    var liveSize: Float,
    var category: String,
    var branchId: Int,
    var personalAccount: Int
)

data class ApartmentDataInput(
    var fullSize: Float? = null,
    var liveSize: Float? = null,
    var category: String? = "",
    var branchId: Int? = null,
    var personalAccount: Int? = null
)

object Apartment : Table() {
    val id = integer("id").autoIncrement()
    val fullSize = float("full_size")
    val liveSize = float("live_size")
    val category = varchar("category", 255)
    val personalAccount = integer("personal_account")
    val branchId = integer("branch_id") references Branch.id

    override val primaryKey = PrimaryKey(id, name = "apartment_pk")

    fun toMap(row: ResultRow): ApartmentData =
        ApartmentData(
            id = row[id],
            fullSize = row[fullSize],
            liveSize = row[liveSize],
            category = row[category],
            personalAccount = row[personalAccount],
            branchId = row[branchId]
        )
}