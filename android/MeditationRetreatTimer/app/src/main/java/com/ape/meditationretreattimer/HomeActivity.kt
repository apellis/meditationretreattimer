package com.ape.meditationretreattimer

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.TimerDao
import com.ape.meditationretreattimer.databinding.ActivityHomeBinding
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
                    timerDao.insert(Timer(input.text.toString(), TimerData(mutableListOf())))
                    refreshTimers()
                }
                .setCancelable(true)
                .setView(input)
            builder.show()
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle(R.string.timers)

        // TODO add option for "never ask me again" once settings are implemented
        val notifManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!notifManager.isNotificationPolicyAccessGranted) {
            val builder = AlertDialog.Builder(this)
                .setTitle("[Optional] Allow Do Not Disturb policy access")
                .setMessage("If you want this app to automatically set and unset Do Not Disturb mode:\n\n1. Choose \"OK\". This will take you to your device's settings.\n2. Select \"Meditation Retreat Timer\" and choose \"Allow\".\n3. Navigate back to this app.")
                .setPositiveButton("OK") { _, _ ->
                    startActivity(Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .setCancelable(true)
            builder.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_item_about -> {
                val builder = AlertDialog.Builder(this)
                    .setTitle("About")
                    .setView(R.layout.about_dialog)
                    .setPositiveButton("OK") { _, _ -> }
                builder.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
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
        val builder = AlertDialog.Builder(this)
            .setTitle("Confirm timer deletion")
            .setMessage("Are you sure you want to delete this timer?")
            .setPositiveButton("Delete") { _, _ ->
                timerDao.delete(timer)
                refreshTimers()
            }
            .setNegativeButton("Cancel") { _, _, -> }
            .setCancelable(true)
        builder.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshTimers() {
        timers.clear()
        timers.addAll(timerDao.getAll())
        binding.timersList.adapter!!.notifyDataSetChanged()
    }
}