package com.ape.meditationretreattimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityEditTimerBinding
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.ui.adapter.BellTimeListItemAdapter

class EditTimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTimerBinding
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(applicationContext)
        val timerDao = db.timerDao()

        timer = timerDao.getById(intent.extras!!.getInt("timerId")!!)[0]
        binding.timerName.text = timer.name

        //binding.segmentsList.adapter = BellTimeListItemAdapter(this, timer.segments)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}