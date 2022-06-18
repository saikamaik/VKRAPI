package com.diploma.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun checkEmail(email: String): Boolean {
    return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
}

val BIRTH_DATE_PATTERN: Pattern = Pattern.compile(
    ""
)

fun formatDate(dateStr: String): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        formatter.parse(dateStr)
    } catch (e: ParseException) {
        null
    }
}