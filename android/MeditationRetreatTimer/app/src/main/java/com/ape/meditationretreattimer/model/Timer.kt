package com.ape.meditationretreattimer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

data class Segment(val name: String, val startTime: LocalTime, val endTime: LocalTime)

data class BellTime(var name: String, val time: LocalTime)

data class TimerData(val bellTimes: MutableList<BellTime>) {
    val segments: Array<Segment>
        get() {
            return bellTimes.zipWithNext().map {
                (fst, snd) -> Segment(fst.name, fst.time, snd.time)
            }.toTypedArray()
        }
}

@Entity
data class Timer(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "timer_data") val timerData: TimerData) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}