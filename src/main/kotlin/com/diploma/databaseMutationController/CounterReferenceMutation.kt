package com.diploma.databaseMutationController

import com.diploma.model.CounterReferenceData
import com.diploma.model.Counter_Reference
import com.diploma.model.CounterReferenceDataInput
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class CounterReferenceMutation {

    fun createCounterReference(data: CounterReferenceDataInput) {
        transaction {
            Counter_Reference.insert {
                it[number] = data.number!!
                it[model] = data.model!!
                it[label] = data.label!!
                it[serviceDate] = DateTime(data.serviceDate!!)
                it[typeId] = data.typeId!!
            }[Counter_Reference.id]
        }
    }

    fun updateCounterReference(id: Int, number: String?, model: String?, label: String?, serviceDate: String?, typeId: Int?) {
        transaction {
            Counter_Reference.update ({Counter_Reference.id eq id}) {
                if(number != null) it[Counter_Reference.number] = number
                if(model != null) it[Counter_Reference.model] = model
                if(label != null) it[Counter_Reference.label] = label
                if(serviceDate != null) it[Counter_Reference.serviceDate] = DateTime(serviceDate)
                if(typeId != null) it[Counter_Reference.typeId] = typeId
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
                Counter_Reference.select { Counter_Reference.serviceDate eq DateTime(serviceDate) }.map { Counter_Reference.toMap(it) }
            }
            typeId != null -> {
                Counter_Reference.select { Counter_Reference.typeId eq typeId }.map { Counter_Reference.toMap(it) }
            }
            else -> Counter_Reference.selectAll().map { Counter_Reference.toMap(it) }
        }
    }
}