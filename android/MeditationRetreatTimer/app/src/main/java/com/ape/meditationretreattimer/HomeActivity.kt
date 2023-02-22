package com.ape.meditationretreattimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.TimerDao
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

        val db = AppDatabase.getDatabase(applicationContext)
        val timerDao = db.timerDao()

        binding.timersList.adapter = TimerListItemAdapter(this, timerDao.getAll(), this)
    }

    override fun onStartClick(timer: Timer) {
        val intent = Intent(this, PlayTimerActivity::class.java)
        intent.putExtra("timerId", timer.id)
        startActivity(intent)
    }

    override fun onEditClick(timer: Timer) {
        val intent = Intent(this, EditTimerActivity::class.java)
        intent.putExtra("timerId", timer.id)
        startActivity(intent)
    }

    override fun onDeleteClick(timer: Timer) {
        Toast.makeText(this, "Delete ${timer.name}", Toast.LENGTH_LONG).show()
        TODO()
    }
}