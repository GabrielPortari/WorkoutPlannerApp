package com.gabrielportari.workoutplannerapp.data.listener
import com.gabrielportari.workoutplannerapp.data.model.Week

interface WeekListener {

    fun onNewClick()

    fun onDeleteClick(id: Int)

    fun onEditClick(week: Week)
}