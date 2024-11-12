package com.gabrielportari.workoutplannerapp.data.model

class Workout(
    val idWorkout: Int = 0,
    val name: String,
    val description: String,
    val exercises: List<Exercise>,
    val NEW_CONTROLER: Boolean = false
) {
}