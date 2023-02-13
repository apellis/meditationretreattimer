package com.ape.meditationretreattimer.data

import com.ape.meditationretreattimer.model.BellTime
import com.ape.meditationretreattimer.model.Timer
import java.time.LocalTime

class TimersRepository {
    companion object {
        private val timers: Array<Timer> = arrayOf(
            Timer(1, "Foo"),
            Timer(2, "Bar"),
            Timer(3, "Baz"),
            Timer(4, "Qux"),
        )
    }

    init {
        timers[0].bellTimes.add(0, BellTime("Foo", LocalTime.parse("08:00:00")))
        timers[0].bellTimes.add(1, BellTime("Bar", LocalTime.parse("08:30:00")))
        timers[0].bellTimes.add(2, BellTime("Baz", LocalTime.parse("09:30:00")))
        timers[0].bellTimes.add(3, BellTime("Qux", LocalTime.parse("10:00:00")))
        timers[0].bellTimes.add(4, BellTime("Garply", LocalTime.parse("11:15:00")))
    }

    fun getAllTimers(): Array<Timer> {
        return timers
    }

    fun getTimer(id: Int): Timer? {
        for (timer in timers) {
            if (timer.id == id) return timer
        }

        return null
    }
}