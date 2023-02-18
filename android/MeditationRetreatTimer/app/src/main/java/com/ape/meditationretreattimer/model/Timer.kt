package com.ape.meditationretreattimer.model

import java.time.LocalTime

data class BellTime(val name: String, val time: LocalTime)  // names the segment following the bell

data class Segment(val name: String, val startTime: LocalTime, val endTime: LocalTime)

data class Timer(val id: Int, val name: String) {
    var bellTimes: MutableList<BellTime> = mutableListOf()

    fun insertShim(pos: Int, durationSecs: Int) {
        TODO()
    }

    val segments: Array<Segment>
        get() {
            return buildList {
                for ((start, end) in bellTimes.zipWithNext()) {
                    add(Segment(start.name, start.time, end.time))
                }
            }.toTypedArray()
        }
}