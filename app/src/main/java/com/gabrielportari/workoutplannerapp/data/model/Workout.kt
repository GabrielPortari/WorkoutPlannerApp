package com.gabrielportari.workoutplannerapp.data.model

class Workout(
    val idWorkout: Int,
    var name: String,
    var description: String,
    var exercises: List<Exercise>,
    val NEW_CONTROLER: Boolean = false
) {
}