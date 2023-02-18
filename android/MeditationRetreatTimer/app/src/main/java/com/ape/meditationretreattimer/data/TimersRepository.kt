package com.ape.meditationretreattimer.data

import com.ape.meditationretreattimer.model.BellTime
import com.ape.meditationretreattimer.model.Timer
import java.time.LocalTime

class TimersRepository {
    /**
     * The current data model is simple: a Timer has a list of bell times that are stored in
     * ascending order by local time, and each bell time has a name. The segment between
     * consecutive bell times is labeled in the UI by the name of its starting bell time. The
     * last bell time's name is ignored.
     *
     * Conceptually, a "Timer" is for a full session, and a session is a subset of a calendar
     * day. A session may be something like alternating sitting and walking segments with a meal
     * break or two and a closing bell.
     *
     * In the future, it would be nice to decouple segments, bells, names, and sounds. For
     * example, perhaps a 2hr sit block has a lower-volume halfway bell. This should not change
     * the fact that there is conceptually one 2hr segment rather than two 1hr segments.
     */

    companion object {
        private val timers: Array<Timer> = arrayOf(
            Timer(1, "Short retreat day"),
            Timer(2, "Foo"),
            Timer(3, "Bar"),
            Timer(4, "Baz"),
        )
    }

    init {
        timers[0].bellTimes = mutableListOf(
            BellTime("Breakfast", LocalTime.parse("08:00:00")),
            BellTime("Sit", LocalTime.parse("08:30:00")),
            BellTime("Walk", LocalTime.parse("09:30:00")),
            BellTime("Sit", LocalTime.parse("10:00:00")),
            BellTime("Lunch", LocalTime.parse("11:15:00")),
            BellTime("Lunch", LocalTime.parse("12:00:00")),
            BellTime("Sit", LocalTime.parse("13:45:00")),
            BellTime("Walk", LocalTime.parse("15:00:00")),
            BellTime("Sit", LocalTime.parse("15:30:00")),
            BellTime("Closing", LocalTime.parse("17:00:00")))
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