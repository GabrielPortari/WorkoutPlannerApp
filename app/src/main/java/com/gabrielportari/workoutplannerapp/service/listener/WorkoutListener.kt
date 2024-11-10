package com.gabrielportari.workoutplannerapp.service.listener

interface WorkoutListener {
    fun onNewClick(id: Int)

    fun onDeleteClick(id: Int)

    fun onEditClick(id: Int)

}