package com.example.sideproject.utils

import java.text.SimpleDateFormat
import java.util.*

fun String?.getTimeZoneFormatDate() : String {
    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    simpleDateFormat.also {
        it.timeZone = TimeZone.getTimeZone("GMT+8")
    }
    var date: Date? = simpleDateFormat.parse(this) ?: return ""
    date = Date(date!!.time)
    simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.format(date)
}