package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityEditTimerBinding
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.ui.adapter.BellTimeListItemAdapter
import com.ape.meditationretreattimer.ui.adapter.EditViewBellTimeListItemAdapter
import com.ape.meditationretreattimer.ui.adapter.OnEditBellTimeItemClickListener

class EditTimerActivity : AppCompatActivity(), OnEditBellTimeItemClickListener {
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

        binding.segmentsList.adapter = EditViewBellTimeListItemAdapter(this, timer.timerData.segments)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onEditClick(timer: Timer) {
        TODO()
    }

    override fun onDeleteClick(timer: Timer) {
        TODO()
        //timerDao.delete(timer)
        //refreshTimers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshBellTimes() {
        TODO()
        //timers.clear()
        //timers.addAll(timerDao.getAll())
        //binding.timersList.adapter!!.notifyDataSetChanged()
    }
}