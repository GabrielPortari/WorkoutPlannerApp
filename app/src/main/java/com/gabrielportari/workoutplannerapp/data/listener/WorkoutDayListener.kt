package com.gabrielportari.workoutplannerapp.data.listener

import com.gabrielportari.workoutplannerapp.data.model.Workout

interface WorkoutDayListener {
    fun onNewClick(day: String)

    fun onDeleteClick(day: String, id: Int)

}