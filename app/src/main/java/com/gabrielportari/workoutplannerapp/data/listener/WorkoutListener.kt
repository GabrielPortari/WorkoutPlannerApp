package com.gabrielportari.workoutplannerapp.data.listener

import com.gabrielportari.workoutplannerapp.data.model.Workout

interface WorkoutListener {
    fun onNewClick()

    fun onDeleteClick(id: Int)

    fun onEditClick(workout: Workout)

}