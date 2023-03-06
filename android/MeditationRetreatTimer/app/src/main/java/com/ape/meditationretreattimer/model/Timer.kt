package com.ape.meditationretreattimer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

data class Segment(val name: String, val startTime: LocalTime, val endTime: LocalTime?)

data class BellTime(var name: String, val time: LocalTime)

data class TimerData(val bellTimes: MutableList<BellTime>) {
    val segments: Array<Segment>
        get() {
            val segments: MutableList<Segment> =  bellTimes.zipWithNext().map {
                (fst, snd) -> Segment(fst.name, fst.time, snd.time)
            }.toMutableList()
            if (!bellTimes.isEmpty()) {
                val lastBellTime = bellTimes[bellTimes.size - 1]
                segments.add(Segment(lastBellTime.name, lastBellTime.time, null))
            }
            return segments.toTypedArray()
        }
}

@Entity
data class Timer(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "timer_data") val timerData: TimerData) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}