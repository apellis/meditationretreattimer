package com.ape.meditationretreattimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ape.meditationretreattimer.data.TimersRepository
import com.ape.meditationretreattimer.databinding.ActivityHomeBinding
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.ui.adapter.OnItemClickListener
import com.ape.meditationretreattimer.ui.adapter.TimerListItemAdapter

class HomeActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timersRepository = TimersRepository()
        binding.timersList.adapter = TimerListItemAdapter(this, timersRepository.getAllTimers(), this)
    }

    override fun onStartClick(timer: Timer) {
        Toast.makeText(this, "Start ${timer.name}", Toast.LENGTH_LONG).show()
    }

    override fun onEditClick(timer: Timer) {
        Toast.makeText(this, "Edit ${timer.name}", Toast.LENGTH_LONG).show()
    }

    override fun onDeleteClick(timer: Timer) {
        Toast.makeText(this, "Delete ${timer.name}", Toast.LENGTH_LONG).show()
    }
}