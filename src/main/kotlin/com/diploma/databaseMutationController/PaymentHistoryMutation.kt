package com.diploma.databaseMutationController

import com.diploma.model.PaymentHistory
import com.diploma.model.PaymentHistoryDataInput
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime

class PaymentHistoryMutation {

    fun createPaymentHistory(data: PaymentHistoryDataInput) {
        transaction {
            PaymentHistory.insert {
                it[date] = DateTime(data.date)
                it[cost] = data.cost!!
                it[penalty] = data.penalty!!
                it[userId] = data.userId!!
                it[apartmentId] = data.apartmentId!!
            }
        }
    }

    fun updatePaymentHistory(id: Int, data: PaymentHistoryDataInput) {
        transaction {
            PaymentHistory.update ({ PaymentHistory.id eq id}) {
                if(data.date != null) it[date] = DateTime(data.date)
                if(data.cost != null) it[cost] = data.cost
                if(data.penalty != null) it[penalty] = data.penalty
                if(data.userId != null) it[userId] = data.userId
                if(data.apartmentId != null) it[apartmentId] = data.apartmentId
            }
        }
    }

    fun deletePaymentHistory(id: Int) {
        transaction {
            PaymentHistory.deleteWhere { PaymentHistory.id eq id }
        }
    }

}