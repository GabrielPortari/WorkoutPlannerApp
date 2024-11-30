package com.gabrielportari.workoutplannerapp.data.model

data class Week(
    val id: Int,
    val name: String,
    val description: String,
    val workoutIdDaySunday: Int,
    val workoutIdDayMonday: Int,
    val workoutIdDayTuesday: Int,
    val workoutIdDayWednesday: Int,
    val workoutIdDayThursday: Int,
    val workoutIdDayFriday: Int,
    val workoutIdDaySaturday: Int,
    val controller: Int
    ){

}