package com.diploma.databaseMutationController

import com.diploma.model.CounterReference
import com.diploma.model.CounterReferenceDataInput
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class CounterReferenceMutation {

    fun createCounterReference(data: CounterReferenceDataInput) {
        transaction {
            CounterReference.insert {
                it[number] = data.number!!
                it[model] = data.model!!
                it[label] = data.label!!
                it[serviceDate] = data.serviceDate!!
                it[typeId] = data.typeId!!
            }[CounterReference.id]
        }
    }

    fun updateCounterReference(id: Int, data: CounterReferenceDataInput) {
        transaction {
            CounterReference.update ({CounterReference.id eq id}) {
                if(data.number != null) it[number] = data.number
                if(data.model != null) it[model] = data.model
                if(data.label != null) it[label] = data.label
                if(data.serviceDate != null) it[serviceDate] = data.serviceDate
                if(data.typeId != null) it[typeId] = data.typeId
            }
        }
    }

    fun deleteCounterReference(id: Int) {
        transaction {
            CounterReference.deleteWhere { CounterReference.id eq id }
        }
    }

}