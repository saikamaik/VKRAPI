package com.diploma.databaseMutationController

import com.diploma.model.Apartment
import com.diploma.model.ApartmentDataInput
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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

    fun updateApartment(id: Int, data: ApartmentDataInput) {
        transaction {
            Apartment.update({
                Apartment.id eq id
            }) {
                if (data.fullSize != null ) it[fullSize] = data.fullSize!!
                if (data.liveSize != null ) it[liveSize] = data.liveSize!!
                if (data.category != null ) it[category] = data.category!!
                if (data.branchId != null ) it[branchId] = data.branchId!!
                if (data.personalAccount != null ) it[personalAccount] = data.personalAccount!!
            }
        }
    }

    fun deleteApartment(id: Int) {
        transaction {
            Apartment.deleteWhere { Apartment.id eq id }
        }
    }

}