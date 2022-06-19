package com.diploma.databaseMutationController

import com.diploma.model.*
import com.diploma.utils.getLocalDate
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.joda.time.DateTime

class PaymentHistoryMutation {

    fun createPaymentHistory(date: String?, cost: Float?, branchId: Int, apartmentId: Int, serviceRecordId: Int?) {

        val costFinal: Float = if (serviceRecordId != null) {
            calculateSingleCost(serviceRecordId, branchId)
        } else calculateCost(branchId, apartmentId)

        transaction {
            PaymentHistory.insert {
                if (date == null) it[PaymentHistory.date] = DateTime(getLocalDate())
                else it[PaymentHistory.date] = DateTime(date)
                if (cost != null) it[PaymentHistory.cost] = costFinal
                it[PaymentHistory.branchId] = branchId
                it[PaymentHistory.apartmentId] = apartmentId
            }
        }
    }

    fun updatePaymentHistory(id: Int, date: String?, cost: Float?, branchId: Int?, apartmentId: Int?) {
        transaction {
            PaymentHistory.update({ PaymentHistory.id eq id }) {
                if (date != null) it[PaymentHistory.date] = DateTime(date)
                if (cost != null) it[PaymentHistory.cost] = cost
                if (branchId != null) it[PaymentHistory.branchId] = branchId
                if (apartmentId != null) it[PaymentHistory.apartmentId] = apartmentId
            }
        }
    }

    fun showPaymentHistory(id: Int?, date: String?, branchId: Int?, apartmentId: Int?): List<PaymentHistoryData> {
        return when {
            id != null -> {
                PaymentHistory.select { PaymentHistory.id eq id }.map { PaymentHistory.toMap(it) }
            }
            date != null -> {
                PaymentHistory.select { PaymentHistory.date eq DateTime(date) }.map { PaymentHistory.toMap(it) }
            }
            branchId != null -> {
                PaymentHistory.select { PaymentHistory.branchId eq branchId }.map { PaymentHistory.toMap(it) }
            }
            apartmentId != null -> {
                PaymentHistory.select { PaymentHistory.apartmentId eq apartmentId }.map { PaymentHistory.toMap(it) }
            }
            else -> PaymentHistory.selectAll().map { PaymentHistory.toMap(it) }
        }
    }

    fun deletePaymentHistory(id: Int) {
        transaction {
            PaymentHistory.deleteWhere { PaymentHistory.id eq id }
        }
    }

    private fun calculateCost(branchId: Int, apartmentId: Int): Float {

        val serviceList = Service
            .select { Service.customWork eq false }
            .map { Service.toMap(it) }

        var costFinal: Float = 0F
        for (service in serviceList) {
            if (service.measureRefId == Measure_Reference.select { Measure_Reference.shortName eq "шт" }
                    .map { it[Measure_Reference.id] }.single()) {
                //Посчитать обычно
            } else {
                Readings.select { Readings.apartmentId eq apartmentId }.map { it[Readings.reading] }
            }

            //или добавить много категорий
            //много категорий звучит как правда кстати
            //типа будет поиск по не кастом ворк
            //потом будет поиск того, что не шт
            //потом будет поиск по категории услуг
            //в категории услуг будет написано такое же название как и в счетчиках
            //поэтому мы берем через айди категории которое лежит в услуге, находим название категории и сравниваем его с типом счетчика
            //берем этот тип счетчика, ищем все счетчики определенных апартаментов
            //и ищем тип нужный

            val cost = Service_Collection
                .select { (Service_Collection.serviceId eq service.id!!) and (Service_Collection.branchId eq branchId) }
                .map { Service_Collection.toCostMap(it) }
            costFinal += cost[cost.lastIndex]
        }
        return costFinal
    }

    private fun checkBasicService(apartmentId: Int, branchId: Int) {

    }

    private fun calculateSingleCost(serviceRecordId: Int, branchId: Int): Float {
        val serviceId = Service_Record.slice(Service_Record.serviceId).select { Service_Record.id eq serviceRecordId }
            .map { Service_Record.toMap(it) }.single()
        return Service_Collection
            .slice(Service_Collection.cost)
            .select { (Service_Collection.serviceId eq serviceId.id) and (Service_Collection.branchId eq branchId) }
            .map { it[Service_Collection.cost] }.single()

    }

}