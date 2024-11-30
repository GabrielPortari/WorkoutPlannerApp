package com.gabrielportari.workoutplannerapp.data.listener

import com.gabrielportari.workoutplannerapp.data.model.Exercise

interface ExerciseListener {
    fun onNewClick()

    fun onDeleteClick(id: Int)

    fun onEditClick(exercise: Exercise)
}