package com.ape.meditationretreattimer.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

data class BellTime(val name: String, val time: LocalTime)  // names the segment following the bell

data class Segment(val name: String, val startTime: LocalTime, val endTime: LocalTime)

@Entity
data class Timer(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "bell_times_json") val bellTimesJson: String) {

    var bellTimes: MutableList<BellTime> = mutableListOf()

    val segments: Array<Segment>
        get() {
            return buildList {
                for ((start, end) in bellTimes.zipWithNext()) {
                    add(Segment(start.name, start.time, end.time))
                }
            }.toTypedArray()
        }
}