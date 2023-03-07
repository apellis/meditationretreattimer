package com.ape.meditationretreattimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioGroup
import com.ape.meditationretreattimer.data.AppDatabase
import com.ape.meditationretreattimer.data.SettingDao
import com.ape.meditationretreattimer.databinding.ActivitySettingsBinding
import com.ape.meditationretreattimer.model.Setting
import com.ape.meditationretreattimer.model.SettingName

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var db: AppDatabase
    private lateinit var settingDao: SettingDao
    private lateinit var settings: MutableMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.settings)

        db = AppDatabase.getDatabase(applicationContext)
        settingDao = db.settingDao()
        loadSettings()

        registerListeners()
    }

    private fun loadSettings() {
        settings = settingDao.getAll().toMutableMap()

        // For each setting: if not already set, set to default value
        var changedAnySettings = false
        for (entry in Setting.DEFAULT_SETTINGS) {
            if (!settings.containsKey(entry.key)) {
                settingDao.set(entry.key, entry.value)
                changedAnySettings = true
            }
        }
        if (changedAnySettings) {
            settings = settingDao.getAll().toMutableMap()
        }

        // Update UI to reflect changes
        if (settings[SettingName.USE_24_HOUR_TIME.name] == "true") {
            binding.hourFormat24.isChecked = true
        } else {
            binding.hourFormat12.isChecked = true
        }
        // binding.lockScreenCheck.isChecked = settings[SettingName.SHOW_ON_LOCK_SCREEN.name] == "true"
        binding.autoDndCheck.isChecked = settings[SettingName.AUTO_SET_DND.name] == "true"
        binding.autoDndPermCheck.isChecked = settings[SettingName.NEVER_ASK_FOR_DND_PERMISSION.name] == "true"
        // binding.autoAirplaneCheck.isChecked = settings[SettingName.AUTO_SET_AIRPLANE.name] == "true"
        // binding.autoAirplanePermCheck.isChecked = settings[SettingName.NEVER_ASK_FOR_AIRPLANE_PERMISSION.name] == "true"
    }

    private fun registerListeners() {
        registerRadioButtonSettingClickListener(
            SettingName.USE_24_HOUR_TIME.name,
            binding.timeFormatGroup,
            mapOf(R.id.hour_format_12 to "false", R.id.hour_format_24 to "true"))
        /*
        registerCheckboxSettingClickListener(
            SettingName.SHOW_ON_LOCK_SCREEN.name,
            binding.lockScreenCheck)
         */
        registerCheckboxSettingClickListener(
            SettingName.AUTO_SET_DND.name,
            binding.autoDndCheck)
        registerCheckboxSettingClickListener(
            SettingName.NEVER_ASK_FOR_DND_PERMISSION.name,
            binding.autoDndPermCheck)
        /*
        registerCheckboxSettingClickListener(
            SettingName.AUTO_SET_AIRPLANE.name,
            binding.autoAirplaneCheck)
        registerCheckboxSettingClickListener(
            SettingName.NEVER_ASK_FOR_AIRPLANE_PERMISSION.name,
            binding.autoAirplanePermCheck)
         */
    }

    private fun registerCheckboxSettingClickListener(settingName: String, checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && (!settings.containsKey(settingName) || settings[settingName] == "false")) {
                settingDao.set(settingName, "true")
            } else if (!isChecked && (!settings.containsKey(settingName) || settings[settingName] == "true")) {
                settingDao.set(settingName, "false")
            }
            settings = settingDao.getAll().toMutableMap()
        }
    }

    private fun registerRadioButtonSettingClickListener(settingName: String, radioGroup: RadioGroup, settingMap: Map<Int, String>) {
        radioGroup.setOnCheckedChangeListener { _, i ->
            if (!settings.containsKey(settingName) || settings[settingName] != settingMap[i]) {
                settingDao.set(settingName, settingMap[i]!!)
            }
            settings = settingDao.getAll().toMutableMap()
        }
    }
}