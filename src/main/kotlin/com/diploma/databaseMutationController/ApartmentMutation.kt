package com.diploma.databaseMutationController

import com.diploma.model.Apartment
import com.diploma.model.ApartmentData
import com.diploma.model.ApartmentDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ApartmentMutation {

    fun createApartment(data: ApartmentDataInput) {
        transaction {
            Apartment.insert {
                it[fullSize] = data.fullSize!!
                it[liveSize] = data.liveSize!!
                it[category] = data.category!!
                it[branchId] = data.branchId!!
                it[personalAccount] = data.personalAccount!!
            }[Apartment.id]
        }
    }

    fun updateApartment(id: Int, fullSize: Float?, liveSize: Float?, category: String?, branchId: Int?, personalAccount: Int? ) {
        transaction {
            Apartment.update({
                Apartment.id eq id
            }) {
                if (fullSize != null ) it[Apartment.fullSize] = fullSize
                if (liveSize != null ) it[Apartment.liveSize] = liveSize
                if (category != null ) it[Apartment.category] = category
                if (branchId != null ) it[Apartment.branchId] = branchId
                if (personalAccount != null ) it[Apartment.personalAccount] = personalAccount
            }
        }
    }

    fun deleteApartment(id: Int) {
        transaction {
            Apartment.deleteWhere { Apartment.id eq id }
        }
    }

    fun showApartment(id: Int?, fullSize: Float?, liveSize: Float?, category: String?, branchId: Int?, personalAccount: Int?): List<ApartmentData> {
        return when {
            id != null -> {
                Apartment.select { Apartment.id eq id }.map { Apartment.toMap(it) }
            }
            fullSize != null -> {
                Apartment.select { Apartment.fullSize eq fullSize }.map { Apartment.toMap(it) }
            }
            liveSize != null -> {
                Apartment.select { Apartment.liveSize eq liveSize }.map { Apartment.toMap(it) }
            }
            category != null -> {
                Apartment.select { Apartment.category eq category }.map { Apartment.toMap(it) }
            }
            branchId != null -> {
                Apartment.select { Apartment.branchId eq branchId }.map { Apartment.toMap(it) }
            }
            personalAccount != null -> {
                Apartment.select { Apartment.personalAccount eq personalAccount }.map { Apartment.toMap(it) }
            }
            else -> Apartment.selectAll().map { Apartment.toMap(it) }
        }
    }
}