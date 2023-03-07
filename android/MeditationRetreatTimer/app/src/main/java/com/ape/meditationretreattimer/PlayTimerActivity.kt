package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.SettingDao
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityPlayTimerBinding
import com.ape.meditationretreattimer.model.Segment
import com.ape.meditationretreattimer.model.SettingName
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
    private lateinit var settingDao: SettingDao
    private lateinit var settings: Map<String, String>
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
        settingDao = db.settingDao()
        settings = settingDao.getAll()

        timer = timerDao.getById(intent.extras!!.getInt("timerId"))[0]
        segments = timer.timerData.segments.toMutableList()
        binding.toolbar.contentDescription = timer.name

        binding.segmentsList.adapter = BellTimeListItemAdapter(this, segments, settings)
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

                if (segments.isEmpty()) {
                    // Session has no bells
                    newPos = RecyclerView.NO_POSITION
                    segmentStr = "(Active timer has no bells)"
                } else if (now >= segments[segments.size - 1].startTime) {
                    // Session is complete
                    newPos = segments.size - 1
                    segmentStr = "All done!"
                } else if (now < segments[0].startTime) {
                    // Session hasn't started yet
                    newPos = BellTimeListItemAdapter.BEFORE_START_POSITION
                    segmentStr = "Waiting to start..."
                } else {
                    newPos = segments.indexOfLast { LocalTime.now() >= it.startTime }
                    segmentStr = "Now: ${segments[newPos].name}"
                }

                val timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern(
                    if (settings[SettingName.USE_24_HOUR_TIME.name] == "true") "HH:mm:ss" else "hh:mm:ss a"))
                binding.segmentNow.text = "$timeStr\n$segmentStr"
                (binding.segmentsList.adapter as BellTimeListItemAdapter)
                    .setSelectedPos(newPos) {
                        Utils.playSound(applicationContext, mediaPlayer, R.raw.bell)
                    }
                handler.postDelayed(this, Utils.TIME_RESOLUTION_MILLIS)
            }
        })

        val notifManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notifManager.isNotificationPolicyAccessGranted &&
            (!settings.containsKey(SettingName.AUTO_SET_DND.name) || settings[SettingName.AUTO_SET_DND.name] == "true")) {

            notifManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Timer bells should no longer ring
        handler.removeCallbacksAndMessages(null)

        // Turn off DND
        val notifManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notifManager.isNotificationPolicyAccessGranted) {
            notifManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        }
    }
}