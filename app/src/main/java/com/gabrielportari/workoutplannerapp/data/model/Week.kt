package com.gabrielportari.workoutplannerapp.data.model

data class Week(
    val id: Int,
    val name: String,
    val description: String,
    var workoutDaySunday: Workout?,
    var workoutdDayMonday: Workout?,
    var workoutDayTuesday: Workout?,
    var workoutDayWednesday: Workout?,
    var workoutdDayThursday: Workout?,
    var workoutDayFriday: Workout?,
    var workoutdDaySaturday: Workout?,
    val controller: Int
    ){

}