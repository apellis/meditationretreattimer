package com.ape.meditationretreattimer.data

import android.content.Context
import androidx.room.*
import com.ape.meditationretreattimer.model.BellTime
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.model.TimerData
import java.time.LocalTime

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

class Converters {
    @TypeConverter
    fun timerDataFromString(value: String?): TimerData? {
        // TODO de-stub
        return TimerData(mutableListOf(
            BellTime("foo", LocalTime.of(13, 0)),
            BellTime("bar", LocalTime.of(14, 0)),
            BellTime("baz", LocalTime.of(14, 30))))
    }

    @TypeConverter
    fun timerDataToString(timerData: TimerData?): String? {
        // TODO de-stub
        return """{"bellTimes":[{"name":"foo","time":123},{"name":"bar","time":456}]}"""
    }
}

@Database(entities = [Timer::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun timerDao(): TimerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .allowMainThreadQueries() // TODO: move stuff off main thread
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}