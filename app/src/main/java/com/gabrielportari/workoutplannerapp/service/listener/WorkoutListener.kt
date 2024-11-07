package com.gabrielportari.workoutplannerapp.service.listener

interface WorkoutListener {
    fun onListClick(id: Int)

    fun onDelete(id: Int)

    fun onEdit(id: Int)

}