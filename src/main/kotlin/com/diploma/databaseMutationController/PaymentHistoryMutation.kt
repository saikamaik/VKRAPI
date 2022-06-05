package com.diploma.databaseMutationController

import com.diploma.model.PaymentHistory
import com.diploma.model.PaymentHistoryDataInput
import com.diploma.utils.getLocalDate
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime

class PaymentHistoryMutation {

    fun createPaymentHistory(data: PaymentHistoryDataInput) {
        transaction {
            PaymentHistory.insert {
                it[date] = DateTime(getLocalDate())
                // it[cost] =
                it[branchId] = data.branchId!!
            }
        }
    }

    fun updatePaymentHistory(id: Int, data: PaymentHistoryDataInput) {
        transaction {
            PaymentHistory.update ({ PaymentHistory.id eq id}) {
                if(data.date != null) it[date] = DateTime(data.date)
                if(data.cost != null) it[cost] = data.cost
                if(data.branchId != null) it[branchId] = data.branchId
            }
        }
    }

    fun deletePaymentHistory(id: Int) {
        transaction {
            PaymentHistory.deleteWhere { PaymentHistory.id eq id }
        }
    }

    fun calculateCost(branchId: Int) {
        //TODO
    }

}