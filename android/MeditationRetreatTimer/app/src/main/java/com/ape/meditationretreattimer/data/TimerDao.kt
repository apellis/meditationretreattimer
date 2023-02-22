package com.ape.meditationretreattimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ape.meditationretreattimer.model.Timer

@Dao
public interface TimerDao {
    @Query("SELECT * FROM timer ORDER BY id ASC")
    fun getAll(): List<Timer>

    @Query("SELECT * FROM timer WHERE id = :id")
    fun getById(id: Int): List<Timer>

    @Insert
    fun insert(timer: Timer)

    @Update
    fun update(timer: Timer)
}