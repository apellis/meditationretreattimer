package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityHomeBinding
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.ui.adapter.OnItemClickListener
import com.ape.meditationretreattimer.ui.adapter.TimerListItemAdapter

class HomeActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var db: AppDatabase
    private lateinit var timerDao: TimerDao
    private lateinit var timers: MutableList<Timer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(applicationContext)
        timerDao = db.timerDao()
        timers = timerDao.getAll().toMutableList()

        binding.timersList.layoutManager = LinearLayoutManager(this)
        binding.timersList.adapter = TimerListItemAdapter(this, timers, this)

        binding.addTimer.setOnClickListener {
            // TODO: set id to a fresh id
            // TODO: popup dialog to set a name
            timerDao.insert(Timer("foo", ""))
            refreshTimers()
        }
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
        timerDao.delete(timer)
        refreshTimers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshTimers() {
        timers.clear()
        timers.addAll(timerDao.getAll())
        binding.timersList.adapter!!.notifyDataSetChanged()
    }
}