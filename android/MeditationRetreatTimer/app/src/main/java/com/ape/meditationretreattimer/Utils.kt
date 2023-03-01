package com.ape.meditationretreattimer

import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Utils {
    companion object {
        fun formatLocalTime(lt: LocalTime): String {
            return lt.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }
}