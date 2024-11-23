package com.gabrielportari.workoutplannerapp.data.model

data class Week(
    val id: Int,
    val name: String,
    val description: String,
    val dayOfWeek: String,
    val workouts: List<Workout>,
    val controller: Int
    ){

}