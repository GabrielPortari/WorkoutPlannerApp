package com.gabrielportari.workoutplannerapp.data.listener

interface WorkoutListener {
    fun onNewClick()

    fun onDeleteClick(id: Int)

    fun onEditClick(id: Int)

}