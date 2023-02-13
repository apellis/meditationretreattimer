package com.ape.meditationretreattimer.model

import java.time.LocalTime

data class BellTime(val name: String, val time: LocalTime)  // names the segment following the bell

data class Timer(val id: Int, val name: String) {
    val bellTimes: MutableList<BellTime> = mutableListOf()

    fun insertShim(pos: Int, durationSecs: Int) {
        TODO()
    }
}