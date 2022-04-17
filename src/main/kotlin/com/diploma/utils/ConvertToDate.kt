package com.diploma.utils

import java.sql.Date

fun convertToSqlDate(dateToConvert: String): Date {
    val date: Date = Date.valueOf(dateToConvert) //converting string into sql date
    return date
}