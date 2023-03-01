package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityEditTimerBinding
import com.ape.meditationretreattimer.model.Segment
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.model.TimerData
import com.ape.meditationretreattimer.ui.adapter.EditViewBellTimeListItemAdapter
import com.ape.meditationretreattimer.ui.adapter.OnEditBellTimeItemClickListener
import java.time.LocalTime

// TODO: re-order functionality, ideally with tag-and-drag
class EditTimerActivity : AppCompatActivity(), OnEditBellTimeItemClickListener {
    private lateinit var binding: ActivityEditTimerBinding
    private lateinit var timer: Timer
    private lateinit var segments: MutableList<Segment>
    private lateinit var db: AppDatabase
    private lateinit var timerDao: TimerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(applicationContext)
        timerDao = db.timerDao()

        timer = timerDao.getById(intent.extras!!.getInt("timerId"))[0]
        segments = timer.timerData.segments.toMutableList()
        binding.timerName.text = timer.name

        binding.segmentsList.adapter = EditViewBellTimeListItemAdapter(this, segments, this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onEditClick(segment: Segment) {
        // TODO: allow editing time, not just name
        val input = EditText(this)
        val builder = AlertDialog.Builder(this)
            .setTitle("Edit segment")
            .setMessage("Give the segment a new name")
            .setPositiveButton("OK") { _, _ ->
                val bellTime = timer.timerData.bellTimes.first { it.time == segment.startTime }
                bellTime.name = input.text.toString()
                timerDao.update(timer)
                refreshBellTimes()
            }
            .setCancelable(true)
            .setView(input)
        builder.show()
    }

    override fun onDeleteClick(segment: Segment) {
        timer.timerData.bellTimes.removeAt(
            timer.timerData.bellTimes.indexOfFirst { it.time == segment.startTime })
        timerDao.update(timer)
        refreshBellTimes()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshBellTimes() {
        segments.clear()
        segments.addAll(timer.timerData.segments.toMutableList())
        binding.segmentsList.adapter!!.notifyDataSetChanged()
    }
}