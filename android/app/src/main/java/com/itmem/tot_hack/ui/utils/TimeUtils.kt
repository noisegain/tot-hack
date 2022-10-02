package com.itmem.tot_hack.ui.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object TimeUtils {
    fun convertDateToTime(instant: Instant): String {
        val time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return String.format("%02d:%02d", time.hour, time.minute)
    }
}
