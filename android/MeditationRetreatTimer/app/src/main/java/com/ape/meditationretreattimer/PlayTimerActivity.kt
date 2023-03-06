package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
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
    private lateinit var handler : Handler

    private val mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener { start() }
        setOnCompletionListener { reset() }
    }

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
        (binding.segmentsList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = timer.name

        // Prevent device lock screen from coming on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        handler = Handler(Looper.getMainLooper())
        handler.post(object: Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                val newPos: Int
                val segmentStr: String
                val now = LocalTime.now()

                if (segments.isEmpty() || now >= segments[segments.size - 1].startTime) {
                    // Session is complete
                    newPos = RecyclerView.NO_POSITION
                    segmentStr = "All done!"
                } else if (now < segments[0].startTime) {
                    // Session hasn't started yet
                    newPos = RecyclerView.NO_POSITION
                    segmentStr = "Waiting to start..."
                } else {
                    newPos = segments.indexOfLast { LocalTime.now() >= it.startTime }
                    segmentStr = "Now: ${segments[newPos].name}"
                }

                val timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                binding.segmentNow.text = "$timeStr\n$segmentStr"
                (binding.segmentsList.adapter as BellTimeListItemAdapter)
                    .setSelectedPos(newPos) { ->
                        Utils.playSound(applicationContext, mediaPlayer, R.raw.bell)
                    }
                handler.postDelayed(this, Utils.TIME_RESOLUTION_MILLIS)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        // Timer bells should no longer ring
        handler.removeCallbacksAndMessages(null)
    }
}