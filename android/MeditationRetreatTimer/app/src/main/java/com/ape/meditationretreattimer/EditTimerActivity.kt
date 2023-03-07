package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.TimeKeyListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TimePicker
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.SettingDao
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityEditTimerBinding
import com.ape.meditationretreattimer.model.BellTime
import com.ape.meditationretreattimer.model.SettingName
import com.ape.meditationretreattimer.model.Timer
import com.ape.meditationretreattimer.ui.adapter.EditViewBellTimeListItemAdapter
import com.ape.meditationretreattimer.ui.adapter.OnEditBellTimeItemClickListener
import java.time.LocalTime
import java.util.*

class EditTimerActivity : AppCompatActivity(), OnEditBellTimeItemClickListener {
    private lateinit var binding: ActivityEditTimerBinding
    private lateinit var timer: Timer
    private lateinit var bellTimes: MutableList<BellTime>
    private lateinit var db: AppDatabase
    private lateinit var timerDao: TimerDao
    private lateinit var settingDao: SettingDao
    private lateinit var settings: Map<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(applicationContext)
        timerDao = db.timerDao()
        settingDao = db.settingDao()
        settings = settingDao.getAll()

        timer = timerDao.getById(intent.extras!!.getInt("timerId"))[0]
        bellTimes = timer.timerData.bellTimes.toMutableList()
        binding.timerName.text = timer.name

        binding.bellTimesList.adapter = EditViewBellTimeListItemAdapter(this, bellTimes, this, settings)

        binding.addBellTime.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.add_bell_time_dialog, null)
            val addBellTimeTime = view.findViewById<TimePicker>(R.id.add_bell_time_time)

            val builder = AlertDialog.Builder(this)
                .setTitle("New bell")
                .setView(view)
                .setPositiveButton("OK") { _, _ ->
                    timer.timerData.bellTimes.add(BellTime(
                        view.findViewById<EditText>(R.id.add_bell_time_name).text.toString(),
                        LocalTime.parse("${addBellTimeTime.hour}:${addBellTimeTime.minute}")))
                    timer.timerData.bellTimes.sortWith { bt1, bt2 ->
                        if (bt1.time >= bt2.time) {
                            1
                        } else if (bt1.time <= bt2.time) {
                            -1
                        } else {
                            0
                        }
                    }
                    timerDao.update(timer)
                    refreshBellTimes()
                }
                .setCancelable(true)
            addBellTimeTime.setIs24HourView(settings[SettingName.USE_24_HOUR_TIME.name] == "true")
            builder.show()
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit timer: ${timer.name}"
    }

    override fun onEditClick(bellTime: BellTime) {
        val input = EditText(this)
        val builder = AlertDialog.Builder(this)
            .setTitle("Edit bell")
            .setMessage("Give the bell a new name")
            .setPositiveButton("OK") { _, _ ->
                bellTime.name = input.text.toString()
                timerDao.update(timer)
                refreshBellTimes()
            }
            .setCancelable(true)
            .setView(input)
        builder.show()
    }

    override fun onDeleteClick(bellTime: BellTime) {
        timer.timerData.bellTimes.removeAt(
            timer.timerData.bellTimes.indexOfFirst { it.time == bellTime.time })
        timerDao.update(timer)
        refreshBellTimes()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshBellTimes() {
        bellTimes.clear()
        bellTimes.addAll(timer.timerData.bellTimes)
        binding.bellTimesList.adapter!!.notifyDataSetChanged()
    }
}