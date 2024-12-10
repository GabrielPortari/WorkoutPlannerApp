package com.gabrielportari.workoutplannerapp.data.model

data class Week(
    val id: Int,
    val name: String,
    val description: String,
    val workoutDaySunday: Workout?,
    val workoutdDayMonday: Workout?,
    val workoutDayTuesday: Workout?,
    val workoutDayWednesday: Workout?,
    val workoutdDayThursday: Workout?,
    val workoutDayFriday: Workout?,
    val workoutdDaySaturday: Workout?,
    val controller: Int
    ){

}