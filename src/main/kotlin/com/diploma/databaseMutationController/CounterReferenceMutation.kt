package com.diploma.databaseMutationController

import com.diploma.model.CounterReferenceData
import com.diploma.model.Counter_Reference
import com.diploma.model.CounterReferenceDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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

    fun showCounterReference(id: Int?, number: String?, model: String?, label: String?, serviceDate: String?, typeId: Int?):List<CounterReferenceData> {
        return when {
            id != null -> {
                Counter_Reference.select { Counter_Reference.id eq id }.map { Counter_Reference.toMap(it) }
            }
            number != null -> {
                Counter_Reference.select { Counter_Reference.number eq number }.map { Counter_Reference.toMap(it) }
            }
            model != null -> {
                Counter_Reference.select { Counter_Reference.model eq model }.map { Counter_Reference.toMap(it) }
            }
            label != null -> {
                Counter_Reference.select { Counter_Reference.label eq label }.map { Counter_Reference.toMap(it) }
            }
            serviceDate != null -> {
                Counter_Reference.select { Counter_Reference.serviceDate eq serviceDate }.map { Counter_Reference.toMap(it) }
            }
            typeId != null -> {
                Counter_Reference.select { Counter_Reference.typeId eq typeId }.map { Counter_Reference.toMap(it) }
            }
            else -> Counter_Reference.selectAll().map { Counter_Reference.toMap(it) }
        }
    }
}