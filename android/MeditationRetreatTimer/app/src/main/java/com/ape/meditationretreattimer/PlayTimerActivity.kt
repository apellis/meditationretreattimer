package com.ape.meditationretreattimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ape.meditationretreattimer.data.TimersRepository
import com.ape.meditationretreattimer.databinding.ActivityPlayTimerBinding
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.ui.adapter.BellTimeListItemAdapter

class PlayTimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayTimerBinding
    private lateinit var timer: Timer

    private val timersRepository = TimersRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timer = timersRepository.getTimer(intent.extras!!.getInt("timerId"))!!
        binding.timerName.text = timer.name

        binding.segmentsList.adapter = BellTimeListItemAdapter(this, timer.segments)
    }
}