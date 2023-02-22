package com.ape.meditationretreattimer

import android.app.Application
import com.ape.meditationretreattimer.data.AppDatabase

class MeditationRetreatTimerApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}