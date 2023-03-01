package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityPlayTimerBinding
import com.ape.meditationretreattimer.model.Segment
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.ui.adapter.BellTimeListItemAdapter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PlayTimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayTimerBinding
    private lateinit var timer: Timer
    private lateinit var segments: MutableList<Segment>
    private lateinit var db: AppDatabase
    private lateinit var timerDao: TimerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(applicationContext)
        timerDao = db.timerDao()

        timer = timerDao.getById(intent.extras!!.getInt("timerId"))[0]
        segments = timer.timerData.segments.toMutableList()
        binding.timerName.text = timer.name

        binding.segmentsList.adapter = BellTimeListItemAdapter(this, segments)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val handler = Handler(Looper.getMainLooper())
        handler.post(object: Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                val timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                val newPos: Int
                val segmentStr: String
                if (LocalTime.now() >= segments[segments.size - 1].endTime) {
                    // Session is complete
                    newPos = RecyclerView.NO_POSITION
                    segmentStr=  "All done!"
                } else {
                    newPos = segments.indexOfLast { LocalTime.now() >= it.startTime }
                    segmentStr = segments[newPos].name
                }
                binding.segmentNow.text = "$timeStr\nNow: $segmentStr"
                (binding.segmentsList.adapter as BellTimeListItemAdapter).setSelectedPos(newPos)
                handler.postDelayed(this, Utils.TIME_RESOLUTION_MILLIS)
            }
        })
    }
}