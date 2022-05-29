package com.diploma.databaseMutationController

import com.diploma.model.Counter_Reference
import com.diploma.model.CounterReferenceDataInput
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class CounterReferenceMutation {

    fun createCounterReference(data: CounterReferenceDataInput) {
        transaction {
            Counter_Reference.insert {
                it[number] = data.number!!
                it[model] = data.model!!
                it[label] = data.label!!
                it[serviceDate] = data.serviceDate!!
                it[typeId] = data.typeId!!
            }[Counter_Reference.id]
        }
    }

    fun updateCounterReference(id: Int, data: CounterReferenceDataInput) {
        transaction {
            Counter_Reference.update ({Counter_Reference.id eq id}) {
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
            Counter_Reference.deleteWhere { Counter_Reference.id eq id }
        }
    }

}