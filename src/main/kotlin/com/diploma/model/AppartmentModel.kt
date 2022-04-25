package com.diploma.model

import com.diploma.model.User.autoIncrement
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class ApartmentData(
    var id: Int? = null,
    var fullSize: Int,
    var liveSize: Int,
    var category: String,
    var brunchId: Int,
    var personalAccount: Int
)

data class ApartmentDataInput(
    val fullSize: Int? = null,
    val liveSize: Int? = null,
    val category: String? = "",
    val brunchId: Int? = null,
    val personalAccount: Int? = null
)

object Apartment: Table() {
    val id = integer("id").autoIncrement()
    val fullSize = integer("full_size")
    val liveSize = integer("live_size")
    val category = varchar("category", 255)
    val personalAccount = integer("personal_account")
    val branchId = integer("branch_id") references Branch.id

    override val primaryKey = PrimaryKey(Apartment.id, name = "apartment_pkey")

    fun toMap(row: ResultRow): ApartmentData =
        ApartmentData(
            id = row[Apartment.id],
            fullSize = row[Apartment.fullSize],
            liveSize = row[Apartment.liveSize],
            category = row[Apartment.category],
            personalAccount = row[Apartment.personalAccount],
            brunchId = row[Apartment.branchId]
        )
}