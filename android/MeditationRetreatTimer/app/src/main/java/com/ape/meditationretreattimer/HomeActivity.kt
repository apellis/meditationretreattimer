package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityHomeBinding
import com.ape.meditationretreattimer.model.BellTime
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.model.TimerData
import com.ape.meditationretreattimer.ui.adapter.OnTimerListItemClickListener
import com.ape.meditationretreattimer.ui.adapter.TimerListItemAdapter

class HomeActivity : AppCompatActivity(), OnTimerListItemClickListener {
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
            val input = EditText(this)
            val builder = AlertDialog.Builder(this)
                .setTitle("New timer")
                .setMessage("Give the new timer a name")
                .setPositiveButton("OK") { _, _ ->
                    timerDao.insert(Timer(input.text.toString(), TimerData(listOf())))
                    refreshTimers()
                }
                .setCancelable(true)
                .setView(input)
                .show()
        }

        setSupportActionBar(binding.toolbar)
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