package com.diploma.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

data class PaymentHistoryData(
    val id: Int,
    val date: String,
    val cost: Float,
    val penalty: Float,
    val userId: Int,
    val apartmentId: Int,
)

data class PaymentHistoryDataInput(
    val date: String,
    val cost: Float,
    val penalty: Float,
    val userId: Int,
    val apartmentId: Int,
)

object PaymentHistory: Table() {
    val id = integer("id").autoIncrement()
    val date = datetime("date")
    val cost = float("cost")
    val penalty = float("penalty")
    val userId = integer("id") references User.id
    val apartmentId = integer("id") references Apartment.id

    override val primaryKey = PrimaryKey(id, name = "payment_history_pk")

    fun toMap(row: ResultRow): PaymentHistoryData =
        PaymentHistoryData(
            id = row[id],
            date = row[date].toString("dd-MM-yyyy"),
            cost = row[cost],
            penalty = row[penalty],
            userId = row[userId],
            apartmentId = row[apartmentId]
        )
}
