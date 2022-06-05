package com.diploma.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getLocalDate(): String {
    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return current.format(formatter)
}